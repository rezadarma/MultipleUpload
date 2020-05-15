<?php

class M_pemilik extends CI_Model
{


    function insertDataPemilik($data){
        $this->db->insert('t_pemilik',$data);
        $cek = $this->db->affected_rows();
        if($cek > 0){
            $id_pemilik = $this->db->insert_id();
            return $id_pemilik;
        }else{
            return 0;
        }
    }

    function insertOtp($data){
        $this->db->insert('data_otp',$data);
        return $this->db->affected_rows();
    }

    function cekOtp($data){
        $query = $this->db->get_where('data_otp',$data);
        return $query->num_rows();
    }

    function deleteOtp($data){
        $this->db->delete('data_otp',$data);
        return $this->db->affected_rows();
    }

    // function cekUsername($username){
    //     $query = $this->db->get_where('data_pribadi',array('username'=>$username));
    //     return $query->num_rows();
    // }

    // function insertStatusApprove($data){
    //     $this->db->insert('status_approve',$data);
    //     $cek = $this->db->affected_rows();
    //     if($cek > 0){
    //         $id_status_approve = $this->db->insert_id();
    //         return $id_status_approve;
    //     }else{
    //         return 0;
    //     }
    // }

    // function insertAtm($data){
    //     $this->db->insert('t_atm',$data);
    //     $cek = $this->db->affected_rows();
    //     if($cek > 0){
    //         $id_atm = $this->db->insert_id();
    //         return $id_atm;
    //     }else{
    //         return 0;
    //     }
    // }

    // function updateStatusApprove($data,$id_approve){
    //     $this->db->update('status_approve',$data,array('id_status_approve'=>$id_approve));
    //     return $this->db->affected_rows();
    // }

    function updateDokumen($data,$id_pemilik){
        $this->db->trans_start();
        $this->db->update('t_pemilik',$data,array('id_pemilik'=>$id_pemilik));
        $this->db->trans_complete();
        return $this->db->trans_status();
    }

    function cekNik($nik){
        $query = $this->db->get_where('t_pemilik',array('nik'=>$nik));
        return $query->row_array();
    }

    // function updateDataPribadi($data,$id_data_pribadi){
    //     $this->db->update('data_pribadi',$data,array('id_data_pribadi'=>$id_data_pribadi));
    //     return $this->db->affected_rows();
    // }

    // function updatePinAtm($data,$id_atm){
    //     return $this->db->update('t_atm',$data,array('id_atm'=>$id_atm));
    // }

    // function getStatusApprove($id_approve){
    //     $query = $this->db->query("SELECT * FROM `status_approve` WHERE id_status_approve = '$id_approve'");
    //     return $query->row_array();
    // }

}