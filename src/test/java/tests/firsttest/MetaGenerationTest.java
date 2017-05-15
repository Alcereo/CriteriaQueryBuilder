package tests.firsttest;

import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alcereo on 27.04.17.
 */
public class MetaGenerationTest {

    @Test
    public void firstTest() throws IOException {

        String className = "MyNewClass";

        List<String> properties = new ArrayList<>();

        properties.add("id");
        properties.add("name");


        // Prepare source somehow.
        String source = "package ru.alcereo.meta; \n" +
                "public class "+className+" \n" +
                "{ \n";
        for (String property:properties){
            source += "    public static String "+property+"() {\n        return \""+property+"\"; \n} \n";
        }
        source+="}";

        // Save source in .java file.
        File root = new File("./lib"); // On Windows running on C:\, this is C:\java.
        File sourceFile = new File(root, className+".java");
        sourceFile.getParentFile().mkdirs();

        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());

    }

    @Test
    public void metaTest(){

//        ru.alcereo.meta.MyNewClass.name();

    }

}
