package com.zy.graduation1.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGeneratorUtils {
    public static void main(String[] args) {
        CodeGeneratorUtils util = new CodeGeneratorUtils();
        util.generateCode();
    }

    /**
     * 执行生成代码
     */
    public void generateCode() {
        // 包路径
        String packageName = "com.zy.graduation1";

        //子工程的名称
        //String projectName = "uc-service";

        generateByTables(null, packageName, "album", "image");
    }

    private void generateByTables(String projectName, String packageName, String... tableNames) {

        // 数据库信息
        String dbUrl = "jdbc:mysql://localhost:3306/graduation";
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("mysql")
                .setDriverName("com.mysql.jdbc.Driver");


        /*InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getOutputDir(projectName) + "/src/main/resources/mapper/"
                        + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);*/

        // 配置
        GlobalConfig config = new GlobalConfig()
                .setActiveRecord(false)
                .setAuthor("zy")
                .setOutputDir("F:/idea/graduation1/src/main/java/")
                .setFileOverride(true)
                .setActiveRecord(true)// 不需要ActiveRecord特性的请改为false
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(false)// XML ResultMap
                .setBaseColumnList(false)// XML columList
                .setKotlin(false) //是否生成 kotlin 代码
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController")
                .setDateType(DateType.ONLY_DATE) //只使用 java.util.date 代替
                .setIdType(IdType.ID_WORKER)

                .setFileOverride(true)
                .setOpen(false);


        StrategyConfig strategyConfig = new StrategyConfig()
                .setTablePrefix("uc_")// 此处可以修改为您的表前缀(数组)
                .setNaming(NamingStrategy.underline_to_camel) // 表名生成策略
                .setColumnNaming(NamingStrategy.underline_to_camel) // 字段生成策略
                .setInclude(tableNames)//修改替换成你需要的表名，多个表名传数组
                //.setExclude(new String[]{"test"}) // 排除生成的表
                .setEntityLombokModel(true) // lombok实体
                .setEntityBuilderModel(false) // 【实体】是否为构建者模型（默认 false）
                .setEntityColumnConstant(false) // 【实体】是否生成字段常量（默认 false）// 可通过常量名获取数据库字段名 // 3.x支持lambda表达式
                .setLogicDeleteFieldName("deleted") // 逻辑删除属性名称

                // 自定义实体父类
                .setSuperEntityClass(packageName + ".entity.SuperEntity")
                // 自定义实体，公共字段
                .setSuperEntityColumns(new String[]{"ctime", "utime"})
                // 自定义 mapper 父类
               //.setSuperMapperClass("com.wogame.db.starter.mybatisPlus.SuperMapper")
                // 自定义 service 父类
                //strategy.setSuperServiceClass("");
                // 自定义 service 实现类父类
                //strategy.setSuperServiceImplClass("");
                .setRestControllerStyle(true)

                // 自定义 controller 父类
                //strategy.setSuperControllerClass("");

                .entityTableFieldAnnotationEnable(true);

        // 包信息配置
        PackageConfig packageConfig = new PackageConfig()
//                .setModuleName("account")
                .setParent(packageName)
                .setController("controller")
                .setEntity("entity")
                .setMapper("mapper")
                .setXml("mapper");

        // 执行器
        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
//                .setCfg(cfg)
//                .setTemplate(new TemplateConfig().setXml(null))
                .execute();
    }

    /**
     * 返回项目路径
     *
     * @param projectName 项目名
     * @return 项目路径
     * @author Terry
     */
    private String getOutputDir(String projectName) {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        int index = path.indexOf(projectName);
        return path.substring(0, index) + projectName;
    }

}
