<?php
//mendefinisikan konstanta
 define('HOST', 'localhost');
 define('USER', 'root');
 define('PASS', '');
 define('DB', 'volleygsonxampp');

//membuat koneksi dengan database
 $koneksi = mysqli_connect(HOST, USER, PASS, DB) or die(mysqli_errno());
?>