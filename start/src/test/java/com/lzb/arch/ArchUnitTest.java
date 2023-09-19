package com.lzb.arch;

import com.lzb.component.exception.BizException;
import com.tngtech.archunit.core.domain.properties.CanBeAnnotated;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.lzb",
        importOptions = {/*ImportOption.DoNotIncludeJars.class 需要依赖其他jar */ImportOption.DoNotIncludeTests.class})
//要扫描的package
public class ArchUnitTest {

    ///////////////////////////////////////////////////////////////////////////
    // 指定后缀名
    ///////////////////////////////////////////////////////////////////////////

    @ArchTest
    public static final ArchRule dto_validate_suffix = classes()
            .that()
            .resideInAPackage("..dto..")
            .should()
            .haveSimpleNameEndingWith("Dto");

    ///////////////////////////////////////////////////////////////////////////
    // Assembler/Converter 不能引用 BizException
    ///////////////////////////////////////////////////////////////////////////

    @ArchTest
    public static final ArchRule assembler_converter_not_access_BizException = noClasses()
            .that()
            .resideInAnyPackage("..assembler..", "..converter..")
            .should()
            .accessClassesThat()
            .areAssignableTo(BizException.class);

    ///////////////////////////////////////////////////////////////////////////
    // domain 不能引用其他包的类
    ///////////////////////////////////////////////////////////////////////////
    /*@ArchTest
    public static final ArchRule domain_should_not_access_other_package = noClasses()
            .that()
            .resideInAPackage("..domain..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..adapter..", "..application..", "..infrastructure..");*/

}