terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "5.4.0"
    }
    tls = {
      source = "hashicorp/tls"
      version = "4.0.4"
    }
    ansible = {
      source = "ansible/ansible"
      version = "1.1.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_vpc" "cloud_project_vpc" {
  # 10.0.0.0/16
  cidr_block = var.vpc_cidr_block
  enable_dns_hostnames = true
  tags = {
    Name = "cloud project vpc"
    "kubernetes.io/cluster/kubernetes" = "owned"
  }
}

data "aws_availability_zones" "available_azs" {
  state = "available"
}

resource "aws_subnet" "cloud_project_public_subnet" {
  count = var.subnet_count.public
  vpc_id = aws_vpc.cloud_project_vpc.id
  # count = 1 => 10.0.1.0/24
  cidr_block = var.public_subnet_cidr_blocks[count.index]
  # count = 1 => public subnet: us-east-1a
  availability_zone = data.aws_availability_zones.available_azs.names[count.index]
  # map_public_ip_on_launch = true
  tags = { 
    Name = "cloud project public subnet"
  }
}

resource "aws_subnet" "cloud_project_private_subnet" {
  count = var.subnet_count.private
  vpc_id = aws_vpc.cloud_project_vpc.id
  # count = 2 => first private subnet: 10.0.101.0/24, second private subnet: 10.0.102.0/24
  cidr_block = var.private_subnet_cidr_blocks[count.index]
  # count = 2 => first private subnet: us-east-1a, second private subnet: us-east-1b
  availability_zone = data.aws_availability_zones.available_azs.names[count.index]
  tags = {
    Name = "cloud project private subnet-${count.index}"
  }
}

resource "aws_internet_gateway" "cloud_project_igw" {
  # attach igw to vpc
  vpc_id = aws_vpc.cloud_project_vpc.id
  tags = { 
    Name = "cloud project internet gateway"
  }
}

resource "aws_route_table" "cloud_project_public_route_table" {
  vpc_id = aws_vpc.cloud_project_vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.cloud_project_igw.id
  }
  tags = {
    Name = "cloud project public route table"
  }
}

resource "aws_route_table_association" "cloud_project_public_route_association" {
  count = var.subnet_count.public
  subnet_id = aws_subnet.cloud_project_public_subnet[count.index].id
  route_table_id = aws_route_table.cloud_project_public_route_table.id
}

resource "aws_route_table" "cloud_project_private_route_table" {
  vpc_id = aws_vpc.cloud_project_vpc.id
  tags = {
    Name = "cloud project private route table"
  }
}

resource "aws_route_table_association" "cloud_project_private_route_association" {
  count = var.subnet_count.private
  subnet_id = aws_subnet.cloud_project_private_subnet[count.index].id
  route_table_id = aws_route_table.cloud_project_private_route_table.id
}

resource "aws_security_group" "cloud_project_sg_flannel" {
  vpc_id = aws_vpc.cloud_project_vpc.id
  name = "flannel-overlay-backend"
  ingress {
    description = "flannel overlay backend"
    protocol = "udp"
    from_port = 8285
    to_port = 8285
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    description = "flannel vxlan backend"
    protocol = "udp"
    from_port = 8472
    to_port =  8472
    cidr_blocks = ["0.0.0.0/0"]
  }
  tags = {
    Name = "flannel overlay backend"
  }
}

resource "aws_security_group" "cloud_project_sg_common" {
  name = "common-ports"
  vpc_id = aws_vpc.cloud_project_vpc.id
  ingress {
    description = "allow SSH"
    protocol = "tcp"
    from_port = 22
    to_port = 22
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    description = "allow HTTP"
    protocol = "tcp"
    from_port = 80
    to_port = 80 
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    description = "allow HTTPS"
    protocol = "tcp"
    from_port = 443
    to_port = 443
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
  tags = { 
    Name = "common ports"
  }
}

resource "aws_security_group" "cloud_project_sg_control_plane" {
  name = "control-plane security group"
  vpc_id = aws_vpc.cloud_project_vpc.id
  ingress {
    description = "API server"
    protocol = "tcp"
    from_port = 6443
    to_port = 6443
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    description = "kubelet API"
    protocol = "tcp"
    from_port = 2379
    to_port = 2380
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    description = "etcd server client API"
    protocol = "tcp"
    from_port = 10250
    to_port = 10250
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    description = "kube scheduler"
    protocol = "tcp"
    from_port = 10259
    to_port = 10259
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    description = "kube controller manager"
    protocol = "tcp"
    from_port = 10257
    to_port = 10257
    cidr_blocks = ["0.0.0.0/0"]
  }
  tags = { 
    Name = "control plane sg"
  }
}

resource "aws_security_group" "cloud_project_sg_worker_node" {
  name = "worker-node security group"
  vpc_id = aws_vpc.cloud_project_vpc.id
  ingress {
    description = "kubelet API"
    protocol = "tcp"
    from_port = 10250
    to_port = 10250
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    description = "NodePort services"
    protocol = "tcp"
    from_port = 30000
    to_port = 32767
    cidr_blocks = ["0.0.0.0/0"]
  }
  tags = { 
    Name = "worker node sg"
  }
}

resource "aws_security_group" "cloud_project_sg_db" {
  name = "database security group"
  vpc_id = aws_vpc.cloud_project_vpc.id
  # the only inbound traffic allowed is from control plane and worker nodes
  ingress {
    description = "allow mysql traffic"
    protocol = "tcp"
    from_port = 3306 
    to_port = 3306 
    security_groups = [aws_security_group.cloud_project_sg_common.id]
  }
  tags = {
    Name = "database sg"
  }
}

resource "tls_private_key" "cloud_project_private_key" {
  algorithm = "RSA"
  rsa_bits  = 4096
  provisioner "local-exec" {
    command = "echo '${self.public_key_pem}' > ./pubkey.pem"
  }
}

resource "aws_key_pair" "cloud_project_key_pair" {
  key_name = var.keypair_name
  public_key = tls_private_key.cloud_project_private_key.public_key_openssh
  provisioner "local-exec" {
    command = "echo '${tls_private_key.cloud_project_private_key.private_key_pem}' > ./private-key.pem"
  }
}

resource "aws_instance" "cloud_project_control_plane" {
  count = 1
  ami = var.ubuntu_ami
  instance_type = "t2.medium"
  key_name = aws_key_pair.cloud_project_key_pair.key_name
  # public subnet, us-east-1a  
  subnet_id = aws_subnet.cloud_project_public_subnet[count.index].id
  associate_public_ip_address = true
  vpc_security_group_ids = [
    aws_security_group.cloud_project_sg_common.id,
    aws_security_group.cloud_project_sg_flannel.id,
    aws_security_group.cloud_project_sg_control_plane.id
  ]
  root_block_device {
    volume_type = "gp2"
    volume_size = 14
  }
  tags = {
    Name = "master"
    Role = "control plane node"
  }
  provisioner "local-exec" {
    command = "echo 'master ${self.public_ip}' >> ./files/hosts"
  }
}

resource "aws_instance" "cloud_project_worker_node" {
  count = 1
  ami = var.ubuntu_ami
  instance_type = "t2.medium"
  key_name = aws_key_pair.cloud_project_key_pair.key_name
  # public subnet, us-east-1a
  subnet_id = aws_subnet.cloud_project_public_subnet[count.index].id
  associate_public_ip_address = true
  vpc_security_group_ids = [
    aws_security_group.cloud_project_sg_flannel.id,
    aws_security_group.cloud_project_sg_common.id,
    aws_security_group.cloud_project_sg_worker_node.id
  ]
  root_block_device {
    volume_type = "gp2"
    volume_size = 8
  }
  tags = {
    Name = "worker"
    Role = "worker node"
  }
  provisioner "local-exec" {
    command = "echo 'worker ${self.public_ip}' >> ./files/hosts"
  }
}

resource "aws_db_subnet_group" "cloud_project_db_subnet_group" {
  # 10.0.101.0/24 (us-east-1a), 10.0.102.0/24 (us-east-1b) 
  subnet_ids = [for subnet in aws_subnet.cloud_project_private_subnet: subnet.id]
  tags = {
    Name = "db subnet group"
  }
}

resource "aws_db_instance" "db_instance" {
  identifier = "database"
  engine = "mysql"
  engine_version = "8.0.35"
  multi_az = false
  username = "root" 
  password = "rootpassword" 
  instance_class = "db.t2.micro"
  allocated_storage = 10
  # (first or second private subnet) = (us-east-1a or us-east-1b)
  db_subnet_group_name = aws_db_subnet_group.cloud_project_db_subnet_group.id
  vpc_security_group_ids = [aws_security_group.cloud_project_sg_db.id]
  skip_final_snapshot = true
  tags = {
    Name = "db"
  }
}

resource "ansible_host" "cloud_project_control_plane_host" {
  depends_on = [
    aws_instance.cloud_project_control_plane
  ]
  count = 1
  name = "control_plane"
  groups = ["masters"]
  variables = {
    ansible_user = "ubuntu"
    ansible_host = aws_instance.cloud_project_control_plane[count.index].public_ip
    ansible_ssh_private_key_file = "./private-key.pem"
    node_hostname = "master"
  }
}

resource "ansible_host" "cloud_project_worker_node_host" {
  depends_on = [
    aws_instance.cloud_project_worker_node
  ]
  count = 1
  name = "worker"
  groups = ["workers"]
  variables = {
    ansible_user = "ubuntu"
    ansible_host = aws_instance.cloud_project_worker_node[count.index].public_ip
    ansible_ssh_private_key_file = "./private-key.pem"
    node_hostname = "worker"
  }
}