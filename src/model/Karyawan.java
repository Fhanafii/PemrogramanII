/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import view.PesanDialog;

/**
 *
 * @author fhana
 */
public class Karyawan {
    private String ktp, nama, password;
    private int ruang;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();
    
    
    public String getKtp(){
        return ktp;
    }
    
    public void setKtp(String ktp){
        this.ktp = ktp;
    }
    
    public String getNama(){
        return nama;
    }
    
    public void setNama(String nama){
        this.nama = nama;
    }
    
    public int getRuang(){
        return ruang;
    }
    
    public void setRuang(int ruang){
        this.ruang = ruang;
    }
    
    public String getPesan(){
        return pesan;
    }
    
    public void setPesan(String pesan){
       this.pesan = pesan;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public Object[][] getList(){
        return list;
    }
    
    public void setList(Object[][] list){
        this.list = list;
    }
    
    public boolean simpan(){
        boolean adaKesalahan = false;
        Connection connection;
        
        if((connection = koneksi.getConnection()) != null){
            int jumlahSimpan = 0;
            boolean simpan = false;
            String SQLStatement = "";
            PreparedStatement preparedStatement;
            ResultSet rset;
            
            try{
                SQLStatement = "select * from tbkaryawan where ktp=?";
                
                preparedStatement = connection.prepareStatement(SQLStatement);
                preparedStatement.setString(1, ktp);
                rset = preparedStatement.executeQuery();
                
                rset.next();
                if(rset.getRow() > 0){
                    if (pesanDialog.tampilkanPilihan("KTP sudah ada\nApakah data diperbaharui?","Konfirmasi", new Object[]{"Ya","Tidak"}) == 0){
                        simpan = true;
                        SQLStatement = "update tbkaryawan set nama=?, ruang=?, password=? where ktp=?";
                        preparedStatement = connection.prepareStatement(SQLStatement);
                        preparedStatement.setString(1, nama);
                        preparedStatement.setInt(2, ruang);
                        preparedStatement.setString(3, password);
                        preparedStatement.setString(4, ktp);
                        
                        jumlahSimpan = preparedStatement.executeUpdate();
                    }
                }else{
                    simpan = true;
                    SQLStatement = "insert into tbkaryawan(ktp, nama, ruang, password) values (?,?,?,?)";
                    preparedStatement = connection.prepareStatement(SQLStatement); 
                     preparedStatement.setString(1, ktp);
                     preparedStatement.setString(2, nama);
                     preparedStatement.setInt(3, ruang);
                     preparedStatement.setString(4, password);
                     jumlahSimpan = preparedStatement.executeUpdate();
                }
                
                if(simpan){
                    if (jumlahSimpan < 1){
                       adaKesalahan = true; 
                       pesan = "Gagal menyimpan data karyawan" ;
                    }
                }
                
                preparedStatement.close();
                rset.close();
                connection.close();
                
            }catch(SQLException ex){
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbkaryawan\n"+ex+"\n"+SQLStatement;
            }
        }else{
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    
    public boolean baca(String ktp){
        boolean adaKesalahan = false;
        Connection connection; 

        this.ktp = "";
        this.nama = "";
        this.ruang = 0;
        this.password = "";
        
        if ((connection = koneksi.getConnection()) != null){
            PreparedStatement preparedStatement;
            ResultSet rset;
            
            try{
                String SQLStatement = "select * from tbkaryawan where ktp=?";
                preparedStatement = connection.prepareStatement(SQLStatement);
                preparedStatement.setString(1, ktp);
                rset = preparedStatement.executeQuery();

                rset.next();
                
                if(rset.getRow()>0){
                    this.ktp = rset.getString("ktp");
                    this.nama = rset.getString("nama");
                    this.ruang = rset.getInt("ruang");
                    this.password = rset.getString("password");
                    
                }else{
                    adaKesalahan = true;
                    pesan = "KTP \""+ktp+"\" tidak ditemukan";
                }
                
                preparedStatement.close();
                rset.close();
                connection.close();
                
            }catch(SQLException ex){
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbkaryawan\n"+ex;
            }
        }else{
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan();
        }
        
        return !adaKesalahan;
    }
    
    public boolean bacaData(){
        boolean adaKesalahan = false;
        Connection connection;
        list = new Object[0][0];
        
        if ((connection = koneksi.getConnection()) != null){
            String SQLStatement;
            PreparedStatement preparedStatement;
            ResultSet rset;
            
            try{
               SQLStatement = "select ktp, nama from tbkaryawan";
                preparedStatement = connection.prepareStatement(SQLStatement);
                rset = preparedStatement.executeQuery();

                rset.next();
                rset.last();
                list = new Object[rset.getRow()][2];
                
                if (rset.getRow()>0){
                    rset.first();
                    int i=0;
                    do {
                        list[i] = new Object[]{rset.getString("ktp"), rset.getString("nama")};
                         i++;
                    }while(rset.next());
                }
                preparedStatement.close();
                rset.close();
                connection.close();   
            }catch(SQLException ex){
              adaKesalahan = true;
              pesan = "Tidak dapat membaca data\n"+ex.getMessage(); 
            }
        }else{
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan();
        }
        
        return !adaKesalahan;
    }
    
    public boolean hapus(String ktp){
        boolean adaKesalahan = false;
        Connection connection;
        
        if((connection = koneksi.getConnection()) != null){
            int jumlahHapus;
            String SQLStatement;
            PreparedStatement preparedStatement;
            
            try{
                SQLStatement = "delete from tbkaryawan where ktp=?";
                preparedStatement = connection.prepareStatement(SQLStatement);
                preparedStatement.setString(1, ktp);
                jumlahHapus = preparedStatement.executeUpdate();
                 
                 if (jumlahHapus < 1){
                    pesan = "Data karyawan dengan KTP "+ktp+" tidak ditemukan";
                    adaKesalahan = true;
                }
                 preparedStatement.close();
                connection.close();
                
            }catch(SQLException ex){
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbkaryawan\n"+ex;
            }
        }else{
          adaKesalahan = true;
          pesan = "Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan();  
        }
        return !adaKesalahan;
    }
}
