package com.example.keepassandroid;

import org.junit.Test;

import static org.junit.Assert.*;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.KeePassFile;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void loadsExtLibrary()
    {
        final String filePath = "/home/enoc/code/keepassJavaTest/test_password_database/test_database.kdbx";
        final String databasePass = "password";

        KeePassDatabase reader = KeePassDatabase.getInstance(filePath);
        KeePassFile database = reader.openDatabase(databasePass);

        Entry sampleEntry = database.getEntryByTitle("javaTest");
        System.out.println("Title : " + sampleEntry.getTitle());
        System.out.println("User: " + sampleEntry.getUsername());
        System.out.println("Password : " + sampleEntry.getPassword());

        assertEquals(4, 2 + 2);
    }
}