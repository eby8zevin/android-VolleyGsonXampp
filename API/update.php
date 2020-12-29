<?php
error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));
require_once('koneksi.php');

$id = $_POST['id'];
	
$nim = $_POST['nim'];
$name = $_POST['name'];
$class = $_POST['class'];

$getdata = mysqli_query($koneksi, "SELECT * FROM data_mhs WHERE mhs_id='$id'");
$rows = mysqli_num_rows($getdata);

$update = "UPDATE data_mhs SET mhs_nim='$nim', 
		                         mhs_name='$name', 
		                         mhs_class='$class'
		                         WHERE mhs_id='$id'";
		                         
$exequery = mysqli_query($koneksi, $update);
$response = array();

if($rows > 0) 
{
    if($exequery)
    {
        $response['status_kode'] = 1;
        $response['status_pesan'] = "Updated Success";
    } else {
        $response['status_kode'] = 0;
        $response['status_pesan'] = "Updated Failed";
    }
} else {
    $response['status_kode'] = 0;
    $response['status_pesan'] = "Updated Failed, Not data selected";
}

header('Content-type: text/javascript');
echo json_encode($response, JSON_PRETTY_PRINT);
?>