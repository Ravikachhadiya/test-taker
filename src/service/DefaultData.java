package service;

import java.util.ArrayList;
import java.util.List;

import model.Test;
import model.User;

public class DefaultData {
	public Test defaultTest(User user) {
		String id;
		String testTitle;
		int marks;
		List<String> questions = new ArrayList<String>();
		List<List<String>> options = new ArrayList<List<String>>();
		List<Integer> answers = new ArrayList<Integer>();
		
		TestMaker testMaker = new TestMaker();
		id = testMaker.uniqueId();
		
		testTitle = "Mathematics";
		marks = 10;
		
		questions.add("1 + 1");
		List<String> option1 = new ArrayList<String>();
		option1.add("2");
		option1.add("0");
		option1.add("11");
		option1.add("10");
		options.add(option1);
		answers.add(1);
		
		questions.add("5 * 2");
		List<String> option2 = new ArrayList<String>();
		option2.add("3");
		option2.add("2.5");
		option2.add("7");
		option2.add("10");
		options.add(option2);
		answers.add(4);
		
		questions.add("8 / 4");
		List<String> option3 = new ArrayList<String>();
		option3.add("32");
		option3.add("4");
		option3.add("16");
		option3.add("2");
		options.add(option3);
		answers.add(4);
		
		questions.add("41 - 6");
		List<String> option4 = new ArrayList<String>();
		option4.add("47");
		option4.add("5");
		option4.add("35");
		option4.add("41");
		options.add(option4);
		answers.add(3);
		
		questions.add("8 ^ 2");
		List<String> option5 = new ArrayList<String>();
		option5.add("16");
		option5.add("32");
		option5.add("64");
		option5.add("4");
		options.add(option5);
		answers.add(3);
		
		Test test = new Test(user, id, testTitle, marks, questions, options, answers);
		user.setTestMake(test);
		
		return test;
	}
}
