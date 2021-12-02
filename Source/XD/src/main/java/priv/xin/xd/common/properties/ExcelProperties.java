package priv.xin.xd.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = { "classpath:excel.properties" }, encoding = "utf-8")
@Configuration
public class ExcelProperties {

	@Value("${template.excel.dbTableInfo}")
	private String dbTableTemplate;

	public String getDbTableTemplate() {
		return dbTableTemplate;
	}

	public void setDbTableTemplate(String dbTableTemplate) {
		this.dbTableTemplate = dbTableTemplate;
	}
}
