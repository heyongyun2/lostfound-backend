package org.example.springboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class PrintService {

    @Value("${print.printer-name}")
    private String printerName;

    @Value("${print.sumatra-path}")
    private String sumatraPath;

    @Value("${print.file-base-path}")
    private String fileBasePath;

    @Value("${print.mock-mode}")
    private boolean mockMode;

    public void submitPrint(String fileName, int copies, boolean isDuplex) throws Exception {
        String filePath = fileBasePath + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在，无法打印");
        }

        // 模拟模式：只打印日志
        if (mockMode) {
            System.out.println("=================== 模拟打印 ===================");
            System.out.println("文件路径：" + filePath);
            System.out.println("打印机：" + printerName);
            System.out.println("份数：" + copies);
            System.out.println("双面：" + isDuplex);
            System.out.println("================================================");
            return;
        }

        // 真实模式：调用 SumatraPDF 静默打印
        StringBuilder cmd = new StringBuilder();
        cmd.append("\"").append(sumatraPath).append("\" ");
        cmd.append("-print-to \"").append(printerName).append("\" ");
        cmd.append("-print-settings \"").append(copies).append("x");
        if (isDuplex) {
            cmd.append(",duplex");
        }
        cmd.append("\" \"").append(filePath).append("\"");

        Runtime.getRuntime().exec(cmd.toString());
    }
}