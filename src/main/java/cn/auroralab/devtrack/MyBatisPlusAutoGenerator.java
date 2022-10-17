package cn.auroralab.devtrack;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MyBatisPlusAutoGenerator {
    public static void main(String[] args) {
        new AutoGenerator()
                .setDataSource(
                        new DataSourceConfig()
                                .setDbType(DbType.MYSQL)
                                .setDriverName("com.mysql.cj.jdbc.Driver")
                                .setUrl("jdbc:mysql://auroralab.mysql.rds.aliyuncs.com:3306/devtrack_database")
                                .setUsername("devtrack")
                                .setPassword("DevTrack@2022")
                )
                .setGlobalConfig(
                        new GlobalConfig()
                                .setOutputDir(System.getProperty("user.dir") + "/src/main/java")
                                .setAuthor("Guanyu Hu")
                                .setOpen(false)
                                .setServiceName("%sService")
                )
                .setPackageInfo(
                        new PackageConfig()
                                .setParent("cn.auroralab.devtrack")
                                .setEntity("entity")
                                .setMapper("mapper")
                                .setService("service")
                                .setServiceImpl("service.impl")
                                .setController("controller")
                )
                .setStrategy(
                        new StrategyConfig()
                                .setInclude(
                                        "accounts",
                                        "project_members",
                                        "project_roles",
                                        "project_tasks",
                                        "projects",
                                        "verification_code_list"
                                )
                                .setNaming(NamingStrategy.underline_to_camel)
                                .setColumnNaming(NamingStrategy.underline_to_camel)
                                .setEntityLombokModel(true)
                )
                .execute();
    }
}
