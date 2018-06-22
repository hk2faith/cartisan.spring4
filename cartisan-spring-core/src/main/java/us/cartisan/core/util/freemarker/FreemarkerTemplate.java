package us.cartisan.core.util.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import us.cartisan.core.util.ResourceResolver;

/**
 * @author Hyungkook Kim
 */
@Slf4j
public class FreemarkerTemplate {
	static Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

	public static String createTemplate(final String resource, Object object) {
		if (StringUtils.isBlank(resource)) {
			return StringUtils.EMPTY;
		}

		Writer out = new StringWriter();
		ResourceResolver resourceResolver = new ResourceResolver(resource);

		String templateClasspath = resourceResolver.getTemplateClasspath();
		String fileName = resourceResolver.getFileName();

		try {
			cfg.setEncoding(Locale.KOREA, "UTF-8");
			cfg.setDefaultEncoding("UTF-8");
			cfg.setClassForTemplateLoading(FreemarkerTemplate.class, templateClasspath);

			Template temp = cfg.getTemplate(fileName, "UTF-8");
			temp.process(object, out);

			return out.toString();
		} catch (IOException e) {
			log.error("createTemplate({}, {}) is failed.", resource, object, e);
			return null;
		} catch (TemplateException e) {
			log.error("createTemplate({}, {}) is failed.", resource, object, e);
			return null;
		} finally {
			try {
				out.flush();
			} catch (IOException e) {}
		}
	}
}