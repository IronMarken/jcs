package it.uniroma2.dicii.isw2.jcs.paramTests;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JCSUniTest {
		
		private JCS instance;
		private LinkedList<HashMap<String, String>> list;
		private HashMap<String, String> map;
		private byte[] keyBytes;
		private byte[] valueBytes;
		
		private static Random r = null;
		
		@BeforeClass
		public static void configureBeforeClass() {
			JCSUniTest.r = new Random();
		}
		
		@Before
		public void configureTest() throws CacheException {
			this.instance = JCS.getInstance("testCache1");
			this.list = this.configureList();
			
		}
		
		private HashMap<String, String> configureMap() {
			this.map = new HashMap<String, String>();
			
			this.keyBytes = new byte[32];
			this.valueBytes = new byte[128];
			
			for(int i = 0; i < 10; i++) {
				JCSUniTest.r.nextBytes( this.keyBytes );
				JCSUniTest.r.nextBytes( this.valueBytes );
			
				this.map.put(new String(this.keyBytes), new String(this.valueBytes));
			}
			
			return map;
		}
		
		private LinkedList<HashMap<String, String>> configureList() {
			this.list = new LinkedList<HashMap<String, String>>();
			
			for ( int i = 0; i < 100; i++) {
				this.list.add(this.configureMap());
			}
			
			return this.list;
		}
		
		@After
		public void testReport() throws CacheException {
			this.instance.clear();
		}
		
		@Test
		public void testJCS() throws CacheException {
			this.instance.put( "some:key", this.list);
			assertEquals( this.list, this.instance.get("some:key"));
		}
		
	
}
