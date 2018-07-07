package com.alauda.tobacco.service;

import com.alauda.tobacco.domain.Commerce;
import com.alauda.tobacco.domain.Industry;
import com.alauda.tobacco.repository.IndustryRepository;
import com.alauda.tobacco.web.rest.util.QRCodeUtil;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class IndustryService {


    @Inject
    private IndustryRepository industryRepository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Industry createIndustry(Industry industryDto, String flag) throws IOException {

        Industry industry = new Industry();

        if (industryDto.getName() != null) {
            industry.setName(industryDto.getName());
        }

        if (industryDto.getManufacturer() != null) {
            industry.setManufacturer(industryDto.getManufacturer());
        }

        if (industryDto.getDate() != null) {
            industry.setDate(industryDto.getDate());
        }

        if (industryDto.getDesc() != null) {
            industry.setDesc(industryDto.getDesc());
        }

        industry = industryRepository.save(industry);

        createImg(industry, null);

        return industry;

//        ByteArrayOutputStream os=new ByteArrayOutputStream();
//        ImageIO.write(image, "png", os);
//        Base64 base64 = new Base64();
//        String base64Img = new String(base64.encode(os.toByteArray()));
//        industry.setQrcode(base64Img);

    }


    public void createImg(Industry industry, List<Commerce> list) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("该产品的基本信息：").append("\n");
        sb.append("------------------------------------").append("\n");
        sb.append("产品名称:").append(industry.getName()).append("\n");
        sb.append("生产厂商:").append(industry.getManufacturer()).append("\n");
        sb.append("生产日期:").append(sdf.format(Date.from(industry.getDate().toInstant()))).append("\n");
        sb.append("产品描述:").append(industry.getDesc()).append("\n");
        sb.append("------------------------------------").append("\n\n");


        if (list != null) {
            sb.append("该产品的增加信息：").append("\n");
            sb.append("------------------------------------").append("\n");
            for(int i = 0; i < list.size(); i++) {
                sb.append("商业公司名称").append(i + 1).append(":").append(list.get(i).getCompanyname()).append("\n");
                sb.append("零售户名称").append(i + 1).append(":").append(list.get(i).getRetailname()).append("\n");
            }
        }
        BufferedImage image = QRCodeUtil.createQRCode(sb.toString());

        File file = new File("qrCodeDir");
        if  (!file.exists() && !file.isDirectory()) {
            boolean is = file.mkdir();
            if (!is) {
                throw new IOException("创建文件夹失败！");
            }
        }
        ImageIO.write(image, "png", new File("qrCodeDir/code" + industry.getId() + ".png"));
    }

}
