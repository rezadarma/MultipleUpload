<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';
date_default_timezone_set('Asia/Jakarta');

class upload extends REST_Controller {




    public function index_post(){
        $header;
        $response;
        
        $filesCount = $this->post('count');
        $config['upload_path'] = './assets/uploads/'; //path folder
        $config['allowed_types'] = 'gif|jpg|png|jpeg|bmp'; //type yang dapat diakses bisa anda sesuaikan
        $config['encrypt_name'] = TRUE; //nama yang terupload nantinya
 
        $this->load->library('upload',$config);
        for ($i=0; $i <=(int)$filesCount ; $i++) { 
            if(!empty($_FILES['file'.$i]['name'])){
                if(!$this->upload->do_upload('file'.$i))
                    $this->upload->display_errors();
            }
        }
        $header=REST_Controller::HTTP_OK;
        $response=array('status'=>true,'message'=>'Upload Image Berhasil');
        
        $this->response($response, $header);
    }
	
}