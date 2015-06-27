package uk.ac.shef.wit.textractor.io;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * An utility class which writes a list of strings to a file, with one entry on a line.
 *
 * @author <a href="mailto:z.zhang@dcs.shef.ac.uk">Ziqi Zhang</a>
 */

/*
    (c) Copyright 2004 Natural Language Processing Group, The University of Sheffield
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:

    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.

    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.

    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

public class ListWriter {

	private PrintWriter _writer;

	public ListWriter(String pathToFile) {
		try {
			_writer = new PrintWriter(new FileWriter(pathToFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListWriter(String pathToFile, boolean append) {
		try {
			_writer = new PrintWriter(new FileWriter(pathToFile, append));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListWriter(File f, boolean append) {
		try {
			_writer = new PrintWriter(new FileWriter(f, append));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (_writer != null) _writer.close();
	}

	/**
	 * Write a line
	 * @param line
	 */
	public void appendLine(String line) {
		_writer.println(line);
	}


}
