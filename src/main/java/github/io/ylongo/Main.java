package github.io.ylongo;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Main {

	public static void main(String[] args) {
		String path = "G:\\Spark大型项目实战\\Spark大型项目实战：电商用户行为分析大数据平台（史上第一套高端大数据项目实战课程）"; // 路径
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return;
		}

		File[] files = f.listFiles();

		assert files != null;

//		List<File> fileList = Arrays.asList(files);

		String str = "第100讲-Spark Streaming：window滑动窗口以及热点搜索词滑动统计案例实战.zip";
		String regex = "第(\\d+)讲";

		Pattern pattern = Pattern.compile(regex);
//		Matcher matcher = pattern.matcher(str);
//		if (matcher.find()) {
//			String group = matcher.group(1);
//			System.out.println(group);
//		}

		
		
		if (files.length > 100) {
			Arrays.stream(files).map(File::getName)
					.map(name -> "#### " + name)
					.map(name -> name.replace(".zip", ""))
					.sorted(Comparator.comparing(name -> {
						Matcher matcher = pattern.matcher(name);
						if (matcher.find()) {
							return Integer.parseInt(matcher.group(1));	
						}
						return -1;
					}))
					.forEach(System.out::println);
		}


//		for (File fs : fileList) {
//			if (fs.isDirectory()) {
//				continue;
//			}
//			System.out.println("## " + fs.getName().replace(".zip", ""));
//		}
	}


	public static User getUser() {
		return new User();
	}
}


class User {

	Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}

class Address {

	Country country;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}

class Country {
	String isocode;

	public String getIsocode() {
		return isocode;
	}

	public void setIsocode(String isocode) {
		this.isocode = isocode;
	}
}





