<?php
include_once('koneksi.php');

$query = "SELECT * FROM data_mhs ORDER BY mhs_nim ASC";
$result = mysqli_query($koneksi, $query);
$array_data = array();

while($baris = mysqli_fetch_assoc($result))
{
  $array_data[]=$baris;
}

header('Content-type: text/javascript');
echo json_encode($array_data, JSON_PRETTY_PRINT);
?>