package com.sourcetrace.eses.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.poi.hpsf.Thumbnail;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;




public class QRCodeGenerator {

    private static final Logger LOGGER = Logger.getLogger(QRCodeGenerator.class.getName());
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final String IMAGE_FORMAT = "PNG";

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.util.barcode.IQRCodeGenerator#generatorQRCode(java.lang.String)
     */
    public static byte[] generatorQRCode(String inputData) {

        String data = null;
        byte[] b = null;

        if (inputData != null && !inputData.equals("")) {

            Charset charset = Charset.forName("ISO-8859-1");
            CharsetEncoder encoder = charset.newEncoder();

            // Convert a string to ISO-8859-1 bytes in a ByteBuffer
            try {
                ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(inputData));
                b = bbuf.array();
            } catch (CharacterCodingException e) {
                LOGGER.info(e.getMessage());
            }
             try {
                data = new String(b, "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                LOGGER.info(e.getMessage());
            }

            // Get a byte matrix for the data
            BitMatrix matrix = null;
            int h = 200;
            int w = 200;
            MultiFormatWriter writer = new MultiFormatWriter();    
            try {
            	Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
    			hintMap.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
    			hintMap.put(EncodeHintType.MARGIN, -1); /* default = 4 */
    			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    			hintMap.put(EncodeHintType.QR_VERSION, 40);
    		
    			
     
                matrix = writer.encode(data, BarcodeFormat.QR_CODE, w, h,hintMap);
          } catch (WriterException e) {
               LOGGER.info(e.getMessage());
           }

            return getBinaryImage(matrix);
        } else {
            return null;
        }

    }

    /**
     * Gets the binary image.
     * @param matrix the matrix
     * @return the binary image
     */
    public static byte[] getBinaryImage(BitMatrix matrix) {

        byte[] qrImageInByte = null;
        int width = matrix.getWidth();
        int height = matrix.getHeight();

        BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrCodeImage.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, IMAGE_FORMAT, baos);
            baos.flush();
            qrImageInByte = baos.toByteArray();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrImageInByte;

    }

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.util.barcode.IQRCodeGenerator#getQRData(java.io.InputStream)
     */
    public String getQRData(byte[] qrCode) {

        return null;
    }


}
