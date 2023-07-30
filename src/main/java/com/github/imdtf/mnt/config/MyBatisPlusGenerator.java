package com.github.imdtf.mnt.config;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.github.imdtf.mnt.dto.PageDTO;
import com.github.imdtf.mnt.exception.BizException;
import com.github.imdtf.mnt.response.Response;
import com.github.imdtf.mnt.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.util.*;

import static com.github.imdtf.mnt.constant.StrConstant.*;

/**
 * 0 *
 * 1 * @Author: DTF
 * 2 * @email: imdtf@qq.com
 * 3 * @Date: 2023/6/10 22:51
 * 4
 */
@Slf4j
public class MyBatisPlusGenerator {

    private static String tableName;

    private static final Properties PROPERTIES = new Properties();

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        String moduleName = "generator" ;
        tableName = scanInput();
        String[] tableNames = {tableName};

        loadGenerateConfig(projectPath);
        AutoGenerator autoGenerator = new AutoGenerator(initDataSourceConfig());
        autoGenerator.global(initGlobalConfig(projectPath));
        autoGenerator.packageInfo(initPackageConfig(projectPath, moduleName));
        autoGenerator.injection(initInjectionConfig());
        autoGenerator.template(initTemplateConfig());
        autoGenerator.strategy(initStrategyConfig(tableNames));
        autoGenerator.execute();
    }

    private static String scanInput() {
        Scanner scanner = new Scanner(System.in);
        log.info("请输入表名:");
        if (scanner.hasNext()) {
            String next = scanner.next();
            if (StringUtils.isNotBlank(next)) {
                if (next.contains(COMMA)) {
                    next = next.substring(0, next.indexOf(COMMA));
                }
                return next;
            }
        }

        throw new BizException("请输入正确的 " + "表名" + "!");
    }

    private static void loadGenerateConfig(String projectPath) {
        final String propertyName = projectPath + "/src/main/resources/generator.properties" ;
        try (FileInputStream inputStream = new FileInputStream(propertyName)) {
            PROPERTIES.load(inputStream);
        } catch (Exception e) {
            throw new BizException("load generator config error", e);
        }
    }

    private static DataSourceConfig initDataSourceConfig() {
        String url = PROPERTIES.getProperty("dataSource.url");
        String username = PROPERTIES.getProperty("dataSource.username");
        String password = PROPERTIES.getProperty("dataSource.password");
        return new DataSourceConfig.Builder(url, username, password)
                .dbQuery(new MySqlQuery())
                .build();
    }

    private static GlobalConfig initGlobalConfig(String projectPath) {
        return new GlobalConfig.Builder()
                .outputDir(projectPath + "/src/main/java")
                .author("DTF")
                .disableOpenDir()
                .enableSwagger()
                .fileOverride()
                .dateType(DateType.ONLY_DATE)
                .commentDate(DATE_TIME_FORMAT)
                .build();
    }

    private static PackageConfig initPackageConfig(String projectPath, String moduleName) {
        return new PackageConfig.Builder()
                .moduleName(moduleName)
                .parent(PROPERTIES.getProperty("package.base"))
                .service("dao")
                .serviceImpl("service")
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper/"))
                .build();
    }

    private static InjectionConfig initInjectionConfig() {
        Map<String, String> customFile = new HashMap<>(2);
        String dtoName = StringUtils.capitalize(tableName) + "DTO.java";
        String voName = StringUtils.capitalize(tableName) + "VO.java";
        customFile.put(dtoName, "/templates/DTO.java.vm");
        customFile.put(voName, "/templates/VO.java.vm");
        Map<String, Object> customMap = new HashMap<>(4);
        customMap.put("response", Response.class.getName());
        customMap.put("pageDTO", PageDTO.class.getName());
        customMap.put("pageVO", PageVO.class.getName());
        return new InjectionConfig.Builder().customFile(customFile).customMap(customMap).build();
    }

    private static TemplateConfig initTemplateConfig() {
        return new TemplateConfig
                .Builder()
                .mapperXml("")
                .build();
    }

    private static StrategyConfig initStrategyConfig(String[] tableNames) {
        StrategyConfig.Builder builder = new StrategyConfig.Builder();
        builder.entityBuilder()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .enableLombok()
                .formatFileName("%s")
                .mapperBuilder()
                .formatXmlFileName("%sMapper")
                .serviceBuilder()
                .formatServiceFileName("%sDao")
                .formatServiceImplFileName("%sService")
                .controllerBuilder()
                .enableRestStyle()
                .formatFileName("%sController");
        if (tableNames.length == 1 && tableNames[0].contains(ASTERISK)) {
            String[] likeStr = tableNames[0].split(UNDERLINE);
            String likePrefix = likeStr[0] + UNDERLINE;
            builder.likeTable(new LikeTable(likePrefix));
        } else {
            builder.addInclude(tableNames);
        }

        return builder.build();
    }
}
