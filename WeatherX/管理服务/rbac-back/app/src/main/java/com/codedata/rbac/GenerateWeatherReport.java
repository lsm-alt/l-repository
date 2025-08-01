package com.codedata.rbac;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateWeatherReport {

    public static void main(String[] args) throws IOException {
        XWPFDocument document = new XWPFDocument();

        // 标题
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(20);
        titleRun.setText("沾化冬枣成熟期");
        titleRun.addBreak();
        titleRun.setText("气象服务专报");

        // 副标题
        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun subRun = subTitle.createRun();
        subRun.setFontSize(14);
        subRun.setText("2024年第1期");
        subRun.addBreak();
        subRun.setText("滨州市沾化区气象台");

        // 正文段落
        String[] paragraphs = {
                "预计7月15～17日我区以晴到多云天气为主，气温较高，光照充足，降水较少，利于沾化冬枣果实膨大。",
                "预计7月18日受副高外围弱冷空气影响，将有一次雷阵雨天气过程，累计降水量3～6毫米，局部10毫米以上。",
                "预计7月19日～21日，副高增强控制，天气以晴间多云为主，气温升高，光照充足。",
                "当前我区冬枣正值膨大中期，预计7月22日前后开始陆续成熟，接近常年略早。"
        };

        for (String text : paragraphs) {
            XWPFParagraph p = document.createParagraph();
            p.setAlignment(ParagraphAlignment.BOTH);
            XWPFRun run = p.createRun();
            run.setFontSize(12);
            run.setText(text);
        }

        // 表格
        XWPFParagraph tableTitle = document.createParagraph();
        tableTitle.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun tableTitleRun = tableTitle.createRun();
        tableTitleRun.setBold(true);
        tableTitleRun.setText("灾害性天气预报");

        XWPFTable table = document.createTable(3, 3);
        table.setWidth("100%");

        // 表头
        table.getRow(0).getCell(0).setText("时间");
        table.getRow(0).getCell(1).setText("天气现象");
        table.getRow(0).getCell(2).setText("影响");

        // 第一行数据
        table.getRow(1).getCell(0).setText("7月18日");
        table.getRow(1).getCell(1).setText("雷阵雨，局部中雨");
        table.getRow(1).getCell(2).setText("对冬枣采摘及晾晒有一定影响");

        // 第二行数据
        table.getRow(2).getCell(0).setText("7月19日-21日");
        table.getRow(2).getCell(1).setText("晴间多云，气温升高");
        table.getRow(2).getCell(2).setText("利于果实上色成熟");

        // 落款
        XWPFParagraph footer = document.createParagraph();
        footer.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun footerRun = footer.createRun();
        footerRun.addBreak();
        footerRun.setText("滨州市沾化区气象台");
        footerRun.addBreak();
        footerRun.setText("2024年7月14日");

        // 输出 Word 文件
        try (FileOutputStream out = new FileOutputStream("沾化冬枣服务专报.docx")) {
            document.write(out);
            System.out.println("Word 文件已生成：沾化冬枣服务专报.docx");
        }
    }
}
