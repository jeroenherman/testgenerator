
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import generator.Config;
import generator.TestClassGenerator;
import generator.TypeGenerator;
import generator.impl.BooleanType;
import generator.impl.ClassType;
import generator.impl.DoubleType;
import generator.impl.FloatType;
import generator.impl.IntType;
import generator.impl.MethodType;
import generator.impl.StringType;

public class GenerateTestClass {

	private static Map<String, TypeGenerator> gens;
	
	private static TypeGenerator classType;
	private static TypeGenerator methodType;

	static {
		gens = new HashMap<String, TypeGenerator>();

		gens.put("String", new StringType());
		gens.put("int", new IntType());
		gens.put("float", new FloatType());
		gens.put("double", new DoubleType());
		gens.put("boolean", new BooleanType());

		classType = new ClassType();
		methodType = new MethodType();
	}

	public static void main(String[] args) throws ClassNotFoundException {
		String outPKG = "be.leerstad.tictactoe.service.dto";
		String inPKG = "be.leerstad.tictactoe.service.dto";

		File[] classesForTest = new File("src/" + inPKG.replace(".", "/")).listFiles();
		for (File file : classesForTest) {
			String dto = file.getPath().substring(4, file.getPath().length() - 5).replace("\\", ".");

			Class classUnderTest = Class.forName(dto);
			Field[] fields = classUnderTest.getDeclaredFields();
			Method[] methods = classUnderTest.getDeclaredMethods();
			StringBuilder sb = new TestClassGenerator(dto, outPKG).generateTestClassDefinition();
			for (Field f : fields) {
				if (gens.get(f.getType().getSimpleName()) == null) {
					classType.generateTest(sb, f.getName());
				} else {
					gens.get(f.getType().getSimpleName()).generateTest(sb, f.getName());
				}
			}
			for (Method m : methods) {
				if (m.getName().startsWith("get") || m.getName().startsWith("set") || m.getName().startsWith("is")) {
					continue;
				}
				methodType.generateTest(sb, m.getName());
			}

			sb.append("}");

			writeTestClass(sb.toString(), dto);
			System.out.println(sb.toString());

		}

	}

	private static void writeTestClass(String s, String dto) {
		try {
			File testfile = new File(
					"src/test/" + (dto.substring(dto.lastIndexOf(".") + 1, dto.length())) + "Test.java");
			if (!testfile.exists()) {
				FileOutputStream fos = new FileOutputStream(testfile);
				fos.write(s.getBytes());
				fos.flush();
				fos.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
