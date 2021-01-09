<?php
error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));
include_once('koneksi.php');

$id = $_GET['id'];
$getdata = mysqli_query($koneksi, "SELECT * FROM data_mhs WHERE mhs_id = '$id'");
$rows = mysqli_num_rows($getdata);

$delete = "DELETE FROM data_mhs WHERE mhs_id = '$id'";
$exedelete = mysqli_query($koneksi, $delete);

$response = array();
if($rows > 0)
{
  if ($exedelete) {
    $response['status_kode'] = 1;
    $response['status_pesan'] = "Deleted Success";
  }else{
    $response['status_kode'] = 0;
    $response['status_pesan'] = "Failed to Delete";
  }
}else{
  $response['status_kode'] = 0;
  $response['status_pesan'] = "Failed to Delete, data Not Found";
}

header('Content-type: text/javascript');
echo json_encode($response, JSON_PRETTY_PRINT);
?>