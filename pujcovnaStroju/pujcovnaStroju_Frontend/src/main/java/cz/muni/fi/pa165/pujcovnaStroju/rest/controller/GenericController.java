package cz.muni.fi.pa165.pujcovnaStroju.rest.controller;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.ServletContextResource;

import cz.muni.fi.pa165.pujcovnastroju.entity.MachineTypeEnum;
import cz.muni.fi.pa165.pujcovnastroju.entity.UserTypeEnum;

/**
 * generic REST controller
 * 
 * @author Michal Merta
 * 
 */
@Controller
@RequestMapping(value = "/rest")
public class GenericController {

	private static final String HEADER_SUCCESS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<response xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://localhost:8080/pa165/xmlt/schema.xsd\" status=\"success\">";

	private static final String HEADER_ERROR = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<response xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://localhost:8080/pa165/xmlt/schema.xsd\" status=\"error\">";
	private @Autowired
	ServletContext servletContext;

	/**
	 * returns XML schema of REST responses
	 * 
	 * @param model
	 * @return
	 * @throws MalformedURLException
	 */
	@RequestMapping("schema.xsd")
	public HttpEntity<byte[]> getSchema(ModelMap model) {

		ServletContextResource res = new ServletContextResource(servletContext,
				"/WEB-INF/schema/schema.xsd");
		try {
			Path path = res.getFile().toPath();
			byte[] schema = Files.readAllBytes(path);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "xml"));
			header.setContentLength(schema.length);
			return new HttpEntity<byte[]>(schema, header);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder builder = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<response staus=\"success\">");
		builder.append("<text>Schema is hidden at the end of the rainbow stealing "
				+ "leprechaun's pot of gold. It's quite sad.</text>");

		return returnXML(builder.toString());
	}

	@RequestMapping("types")
	public HttpEntity<byte[]> getTypes(ModelMap model) {

		StringBuilder builder = new StringBuilder(HEADER_SUCCESS);
		builder.append("<availableTypes>");
		builder.append("<machineTypes>");
		for (MachineTypeEnum type : MachineTypeEnum.class.getEnumConstants()) {
			builder.append("<type>" + type.name() + "</type>");
		}
		builder.append("</machineTypes>");

		builder.append("<userTypes>");
		for (UserTypeEnum type : UserTypeEnum.class.getEnumConstants()) {
			builder.append("<type>" + type.name() + "</type>");
		}
		builder.append("</userTypes>");
		builder.append("</availableTypes>");

		builder.append("</response>");
		return returnXML(builder.toString());
	}

	/**
	 * creates {@link HttpEntity} for given xml string
	 * 
	 * @param xml
	 * @return
	 */
	public static HttpEntity<byte[]> returnXML(String xml) {
		byte content[] = xml.getBytes();
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "xml"));
		header.setContentLength(content.length);
		return new HttpEntity<byte[]>(content, header);
	}

	/**
	 * creates {@link HttpEntity} for given error message
	 * 
	 * @param message
	 * @return
	 */
	public static HttpEntity<byte[]> returnErrorXML(String message) {
		StringBuilder builder = new StringBuilder();
		builder.append(HEADER_ERROR);
		builder.append("<message>" + StringEscapeUtils.escapeXml(message)
				+ "</message>");
		builder.append("</response>");
		return returnXML(builder.toString());
	}

	public static HttpEntity<byte[]> returnErrorXML(List<String> messages) {
		StringBuilder builder = new StringBuilder();
		builder.append(HEADER_ERROR);
		for (String message : messages) {
			builder.append("<message>" + StringEscapeUtils.escapeXml(message)
					+ "</message>");
		}
		builder.append("</response>");
		return returnXML(builder.toString());
	}

	/**
	 * creates {@link HttpEntity} for given success message
	 * 
	 * @param message
	 * @return
	 */
	public static HttpEntity<byte[]> returnSuccessXML(String message) {
		StringBuilder builder = new StringBuilder(HEADER_SUCCESS);

		builder.append("<message>" + StringEscapeUtils.escapeXml(message)
				+ "</message>");
		builder.append("</response>");
		return returnXML(builder.toString());
	}

	/**
	 * formats output XML document
	 * 
	 * @param input
	 * @return
	 */
	public static String ormatStringAsXML(String input) {
		try {
			Source xmlInput = new StreamSource(new StringReader(input));
			StringWriter stringWriter = new StringWriter();
			StreamResult xmlOutput = new StreamResult(stringWriter);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			transformerFactory.setAttribute("indent-number", 4);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(xmlInput, xmlOutput);
			return xmlOutput.getWriter().toString();
		} catch (TransformerException e) {
			// TODO log better
			e.printStackTrace();
			return input;
		}
	}

	/**
	 * authentication for REST purposes, many kittens were tortured to death because of this.
	 */
	static void defaultRestAuth() {
		SecurityContextHolder.getContext().setAuthentication(
				new Authentication() {

					@Override
					public String getName() {
						return "rest";
					}

					@Override
					public void setAuthenticated(boolean arg0)
							throws IllegalArgumentException {
					}

					@Override
					public boolean isAuthenticated() {
						return true;
					}

					@Override
					public Object getDetails() {
						return null;
					}

					@Override
					public Object getCredentials() {
						return null;
					}

					@Override
					public Collection<? extends GrantedAuthority> getAuthorities() {
						Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
						authorities.add(new SimpleGrantedAuthority(UserTypeEnum.ADMINISTRATOR.name()));
						return authorities;
					}

					@Override
					public Object getPrincipal() {
						return null;
					}
				});
	}
}
