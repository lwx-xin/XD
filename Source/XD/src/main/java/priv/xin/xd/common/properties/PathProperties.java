package priv.xin.xd.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

@PropertySource(value = { "classpath:path.properties" }, encoding = "utf-8")
@Configuration
public class PathProperties {

	/**
	 * html文件真实路径
	 */
	@Value("${html.real.path}")
	private String htmlRealPath;
	/**
	 * html文件虚拟路径
	 */
	@Value("${html.virtual.path}")
	private String htmlVirtualPath;

	@Value("${file.save.real.path}")
	private String fileSaveRealPath;

	@Value("${file.save.virtual.path}")
	private String fileSaveVirtualPath;

	public String getHtmlRealPath() {
		return htmlRealPath;
	}

	public String getFileSaveRealPath() {
		return fileSaveRealPath;
	}

	public String getFileSaveVirtualPath() {
		return fileSaveVirtualPath;
	}

	public String getHtmlVirtualPath() {
		return htmlVirtualPath;
	}
}
