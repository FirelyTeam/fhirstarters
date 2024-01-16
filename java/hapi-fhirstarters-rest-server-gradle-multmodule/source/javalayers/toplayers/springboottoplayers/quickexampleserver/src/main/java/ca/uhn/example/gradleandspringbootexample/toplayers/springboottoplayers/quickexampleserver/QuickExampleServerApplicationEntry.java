package ca.uhn.example.gradleandspringbootexample.toplayers.springboottoplayers.quickexampleserver;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
//@ServletComponentScan
@Configuration
@ImportResource({"classpath*:applicationContext.xml"})

public class QuickExampleServerApplicationEntry { //extends SpringBootServletInitializer {

   public static final String LOG_MSG_JAVA_CLASS_PATH_FILE_PATH = "SpringBootApplication Start.  java.class.path.file.path=\"{}\"";

   public static final String LOG_MSG_GET_BEAN_DEFINITION_NAME = "SpringBootApplication Start.  getBeanDefinitionName=\"{}\"";

   public static final String LOG_MSG_XML_RESOURCE_FILENAME = "SpringBootApplication Start.  Xml.Resource.Filename=\"{}\"";

   private static Logger logger = LoggerFactory
      .getLogger(QuickExampleServerApplicationEntry.class);

   public static void main(final String[] args) {
      try {

         URL resource = QuickExampleServerApplicationEntry.class.getResource("/applicationContext.xml");
         if (null == resource || StringUtils.isBlank(resource.getPath())) {
            throw new FileNotFoundException("applicationContext.xml not found.  The entry dependency injection file must be applicationContext.xml");
         }

         org.springframework.context.ConfigurableApplicationContext applicationContext = SpringApplication.run(QuickExampleServerApplicationEntry.class, args);

         if (logger.isDebugEnabled()) {
            Resource[] items = getXMLResources();
            for (Resource item : items) {
               logger.debug(LOG_MSG_XML_RESOURCE_FILENAME, item.getFilename());
            }

            List<File> list = getFiles(System.getProperty("java.class.path"));
            for (File file : list) {
               logger.debug(LOG_MSG_JAVA_CLASS_PATH_FILE_PATH, file.getPath());
            }

            String[] beanNames = applicationContext.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
               logger.debug(LOG_MSG_GET_BEAN_DEFINITION_NAME, beanName);
            }
         }

      } catch (BeanCreationException | IOException ex) { // | FileNotFoundException ex) {
         Throwable realCause = unwrap(ex);
         logger.error(realCause.getMessage(), realCause);
      }
   }

   private static Resource[] getXMLResources() throws IOException {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);

      Resource[] returnItems = resolver.getResources("classpath*:*.xml");

      Arrays.sort(returnItems, new Comparator<Resource>() {
         public int compare(final Resource a, final Resource b) {
            return Objects.requireNonNull(a.getFilename()).compareTo(Objects.requireNonNull(b.getFilename()));
         }
      });

      return returnItems;
   }

   public static List<File> getFiles(final String paths) {
      List<File> filesList = new ArrayList<File>();
      for (final String path : paths.split(File.pathSeparator)) {
         final File file = new File(path);
         if (file.isDirectory()) {
            recurse(filesList, file);
         } else {
            filesList.add(file);
         }
      }
      return filesList;
   }

   private static void recurse(final List<File> filesList, final File f) {
      File[] list = f.listFiles();
      for (File file : list) {
         if (file.isDirectory()) {
            recurse(filesList, file);
         } else {
            filesList.add(file);
         }
      }
   }

   public static Throwable unwrap(final Throwable ex) {
      if (ex != null && BeanCreationException.class.isAssignableFrom(ex.getClass())) {
         return unwrap(ex.getCause());
      } else {
         return ex;
      }
   }
}
