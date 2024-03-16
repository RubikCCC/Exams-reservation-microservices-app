output "control_plane_ip" {
  value = aws_instance.cloud_project_control_plane[0].public_ip
}

output "worker_node_ip" {
  value = aws_instance.cloud_project_worker_node[0].public_ip
}