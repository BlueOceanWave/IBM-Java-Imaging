package com.ctn;


import nom.tam.fits.*;


public class FitsTesting{

    public static void main(String[] args) {
        try {
            Fits f = new Fits("C:/Users/nadee/Downloads/Test Fits Files/rosat_pspc_rdf2_3_bk1.fits");
            BasicHDU<?> hdu = f.readHDU();

            nom.tam.util.Cursor<String, HeaderCard> header = f.getHDU(0).getHeader().iterator();
            

            while (header.hasNext())
                System.out.println(header.next().toString().trim());
                
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
