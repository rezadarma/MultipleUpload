<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Rest_server extends CI_Controller {

    public function index()
    {
        // $this->load->helper('url');

        // $this->load->view('rest_server');
        $time = strtotime('20200217135536');

        $newformat = date('Y-m-d H:i:s',$time);

        echo $newformat;
    }
}
