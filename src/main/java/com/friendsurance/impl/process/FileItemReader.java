/**
 * 
 */
package com.friendsurance.impl.process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.friendsurance.impl.model.Member;
import com.friendsurance.processing.ItemReader;

/**
 * read user information from a flat file line by line
 * 
 * @author durrah
 *
 */
public class FileItemReader implements ItemReader<Member> {
	private BufferedReader reader;

	public FileItemReader(String filePath) throws IOException {
		reader = new BufferedReader(new FileReader(filePath));
	}

	@Override
	public Member read() {
		Member member = null;
		try {
			String line = reader.readLine();
			if (line == null) {
				reader.close();
				return new Member.NullMember("", false, 0, 0);
			}
			member = Member.fromString(line);
		} catch (Exception e) {
			// ignore
		}
		return member;
	}
}