/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import javax.swing.JOptionPane;
import model.Karyawan;
import model.Pekerjaan;
import model.Gaji;
import view.FormLihatKaryawan;
import view.FormLihatPekerjaan;
import view.FormUtama;


/**
 *
 * @author fhana
 */
public class GajiController {
    
    private final Karyawan karyawan = new Karyawan();
    private final Pekerjaan pekerjaan = new Pekerjaan();
    private final Gaji gaji = new Gaji();
    private FormLihatKaryawan formLihatKaryawan;
    private FormLihatPekerjaan formLihatPekerjaan;
    
    public void cariKaryawan(javax.swing.JTextField ktp){
        if (!ktp.getText().equals("")){
            if (karyawan.baca(ktp.getText())){
                FormUtama.formGaji.setNama(karyawan.getNama());
                FormUtama.formGaji.setRuang( Integer.toString( karyawan.getRuang()));
                FormUtama.formGaji.clearGajiTable();
                
                int jumlahGaji = 0;
                
                if(gaji.baca(ktp.getText())){
                    Object[][] listGaji = gaji.getListGaji();
                    FormUtama.formGaji.clearGajiTable();
                    
                    if(listGaji.length>0){
                        for (int i=0; i<listGaji.length;i++){
                            if (!((String)listGaji[i][0]).equals("")){
                                String namaPekerjaan="";
                                if (pekerjaan.baca((String) listGaji[i][0])){
                                    namaPekerjaan = pekerjaan.getNamaPekerjaan();
                                }
                                FormUtama.formGaji.setTambahGaji(new Object[]{listGaji[i][0],namaPekerjaan,listGaji[i][1],listGaji[i][2],listGaji[i][3]});
                                jumlahGaji++;
                            }
                        }
                    }
                }
                
                if(jumlahGaji == 0){
                    FormUtama.formGaji.setTambahGaji(new Object[]{});
                }
            }else{
                FormUtama.formGaji.setNama("");
                FormUtama.formGaji.setRuang("");
                FormUtama.formGaji.clearGajiTable();
                FormUtama.formGaji.setTambahGaji(new Object[]{});
                
                JOptionPane.showMessageDialog(null, karyawan.getPesan(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        }else{
           JOptionPane.showMessageDialog(null,"ktp tidak boleh kosong\n","Kesalahan",JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    public void tampilkanFormLihatKaryawan(){
        formLihatKaryawan = new FormLihatKaryawan(null, true);
        
        if(karyawan.bacaData()){
            formLihatKaryawan.tampilkanData(karyawan.getList());
            formLihatKaryawan.setVisible(true);
            
            if(!formLihatKaryawan.getKtpDipilih().equals("")){
                
                if((karyawan.baca(formLihatKaryawan.getKtpDipilih()))){
                    FormUtama.formGaji.setKtp(karyawan.getKtp());
                    FormUtama.formGaji.setNama(karyawan.getNama());
                    FormUtama.formGaji.setRuang(Integer.toString(karyawan.getRuang()));
                    FormUtama.formGaji.clearGajiTable();
                    int jumlahGaji=0;
                    
                    if(gaji.baca(formLihatKaryawan.getKtpDipilih())){
                        Object[][] listGaji = gaji.getListGaji();
                        FormUtama.formGaji.clearGajiTable();
                        
                        if (listGaji.length>0){
                            for (int i=0; i<listGaji.length;i++){
                                if(!((String)listGaji[i][0]).equals("")){
                                    String namaPekerjaan="";
                                    
                                    if(pekerjaan.baca((String)listGaji[i][0])){
                                        namaPekerjaan = pekerjaan.getNamaPekerjaan();
                                    }
                                    
                                    FormUtama.formGaji.setTambahGaji(new Object[]{listGaji[i][0],namaPekerjaan,listGaji[i][1],listGaji[i][2],listGaji[i][3]});
                                    jumlahGaji++;
                                }
                            }
                        }
                    }
                    
                    if(jumlahGaji == 0){
                       FormUtama.formGaji.setTambahGaji(new Object[]{});
                    }
                }else{
                   JOptionPane.showMessageDialog(null, karyawan.getPesan());
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, karyawan.getPesan());
        }
    }
    
    public void tampilkanFormLihatPekerjaan(){
        formLihatPekerjaan = new FormLihatPekerjaan(null,true);
        
        if(pekerjaan.bacaData()){
           formLihatPekerjaan.tampilkanData(pekerjaan.getList());
           formLihatPekerjaan.setVisible(true);
           
           if(!formLihatPekerjaan.getKodePekerjaanDipilih().equals("")){
               
               if(pekerjaan.baca(formLihatPekerjaan.getKodePekerjaanDipilih())){
                   FormUtama.formGaji.setTambahGaji(new Object[]{pekerjaan.getKodePekerjaan(),pekerjaan.getNamaPekerjaan(),"","",""});
               }else{
                   FormUtama.formGaji.setTambahGaji(new Object[]{formLihatPekerjaan.getKodePekerjaanDipilih(),"","","",""});
               }
           }
        }else{
            JOptionPane.showMessageDialog(null, pekerjaan.getPesan());
        }
    }
    
    
    public void cariPekerjaan(String kodePekerjaan){
        if (pekerjaan.baca(kodePekerjaan)){
            FormUtama.formGaji.setNamaPekerjaan( pekerjaan.getNamaPekerjaan());
        }else{
            FormUtama.formGaji.setNamaPekerjaan(""); 
            FormUtama.formGaji.hapusGaji();
            JOptionPane.showMessageDialog(null, pekerjaan.getPesan(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void simpan(javax.swing.JTextField ktp, javax.swing.JTable gajiTable){
        if (!ktp.getText().equals("")){
            gaji.setKtp(ktp.getText());
            Object[][] listGaji = new Object[gajiTable.getRowCount()][4];

            
            for (int i=0; i <gajiTable.getRowCount();i++){
            listGaji[i][0] = gajiTable.getValueAt(i, 0);
            listGaji[i][1] = gajiTable.getValueAt(i, 2);
            listGaji[i][2] = gajiTable.getValueAt(i, 3);
            listGaji[i][3] = gajiTable.getValueAt(i, 4);
            }
            
            gaji.setListGaji(listGaji);
            
            if(gaji.simpan){
                FormUtama.formGaji.setKtp("");
                FormUtama.formGaji.setNama("");
                FormUtama.formGaji.setRuang("");
                FormUtama.formGaji.clearGajiTable();
                FormUtama.formGaji.setTambahGaji(new Object[]{});
            }else{
              JOptionPane.showMessageDialog(null, gaji.getPesan());  
            }
        }else{
           JOptionPane.showMessageDialog(null,"ktp tidak boleh kosong\n","Kesalahan",JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    
    public void cetakLaporan(javax.swing.JComboBox ruangComboBox){
        int ruang=0; 

        if (ruangComboBox.getSelectedIndex()>0){
            ruang = Integer.parseInt(ruangComboBox.getSelectedItem().toString());
        }
        
        if(!gaji.cetakLaporan(ruang)){
            JOptionPane.showMessageDialog(null,gaji.getPesan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
        }
    }
}
