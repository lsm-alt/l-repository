package com.codedata.rbac.utils;

import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;
import com.codedata.rbac.service.impl.ForecastServiceImpl;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class WordTemplateUtils {
    private final static Logger logger = LoggerFactory.getLogger(ForecastServiceImpl.class);

    // 替换段落中的变量
    private static void replaceInParagraph(XWPFParagraph paragraph, Map<String, String> params) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs == null) return;

        for (int i = 0; i < runs.size(); i++) {
            String text = runs.get(i).getText(0);
            if (text != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String key = "${" + entry.getKey() + "}";
                    if (text.contains(key)) {
                        text = text.replace(key, entry.getValue());
                        runs.get(i).setText(text, 0);
                    }
                }
            }
        }
    }
    public static void replaceInDocument(String inputPath,String outputPath, Map<String, String> params) {

        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(Files.newInputStream(Paths.get(inputPath)));
        } catch (IOException e) {
            logger.error(ErrorCode.get(ErrorCode.ERR_FILE_IN),e);
            throw new ServiceException(ErrorCode.ERR_FILE_IN, e );
        }

        // 替换段落中的占位符
        for (XWPFParagraph para : doc.getParagraphs()) {
            replaceInParagraph(para, params);
        }

        // 替换表格中的占位符
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph para : cell.getParagraphs()) {
                        replaceInParagraph(para, params);
                    }
                }
            }
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outputPath);
            doc.write(out);
            out.close();
            doc.close();
        } catch (IOException e) {
            logger.error(ErrorCode.get(ErrorCode.ERR_FILE_OUT),e);
            throw new ServiceException(ErrorCode.ERR_FILE_OUT, e );
        }
    }
}
