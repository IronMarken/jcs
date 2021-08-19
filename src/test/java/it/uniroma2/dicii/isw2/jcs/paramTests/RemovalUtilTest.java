package it.uniroma2.dicii.isw2.jcs.paramTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value=Parameterized.class)
public class RemovalUtilTest{
	
	private JCS instance;
	private int start;
	private int end;
	private String memName;
	private boolean check;
	
	@Parameters
	public static Collection<Object[]> params() {
		return Arrays.asList(new Object[][] {
			{false, 1,1, "testCache1"},
			{false,2,1, "testCache1"},
			{false,3,5, "testCache1"},
			{false,-1,2, "testCache1"},
			{true, 1,-1, "testCache1"},
			{false, -1,-2, "testCache1"}
		});
	}
	
	public RemovalUtilTest(boolean check, int start, int end, String name) {
		this.configureValues(check, start, end, name);
	}
	
	private void configureValues(boolean check,int start, int end, String name) {
		this.check = check;
		this.start = start;
		this.end = end;
		this.memName = name;
	}
	
	@Before
	public void configureInstance() throws CacheException {
		this.instance = JCS.getInstance(this.memName);
	}
	
	@After
	public void resetInstance() throws CacheException{
		this.instance.clear();
	}
	
	@Test
	public void runTestPutThenRemoveCategorical() throws CacheException {
		
		for(int i = this.start; i<= this.end; i++) {
			this.instance.put( i + ":key", "data" + i );
		}
		
		for(int i = this.end; i >= this.start; i--) {
			String res = (String) this.instance.get(i + ":key");
			if( res == null) {
				 assertNotNull( "[" + i + ":key] should not be null", res );
			}
		}
		
		System.out.println( "Confirmed that " + ( end - start ) + " items could be found" );

        for ( int i = this.start; i <= this.end; i++ )
        {
            this.instance.remove( i + ":" );
            assertNull( this.instance.get( i + ":key" ) );
        }
        System.out.println( "Confirmed that " + ( this.end - this.start ) + " items were removed" );

        System.out.println( this.instance.getStats() );
	}
	
	
	@Test
	public void runPutInRange() throws CacheException {
		for ( int i = this.start; i <= this.end; i++ )
        {
            this.instance.put( i + ":key", "data" + i );
        }

        for ( int i = this.end; i >= this.start; i-- )
        {
            String res = (String) this.instance.get( i + ":key" );
            if ( res == null )
            {
                assertNotNull( "[" + i + ":key] should not be null", res );
            }
        }
	} 
	
	@Test
	public void runGetInRange() {
		
		for( int i = this.end; i >= this.start; i--) {
			String res = (String) this.instance.get(i + ":key");
			if(check && res == null ) {
				assertNotNull("[" + i + ":key] should not be null", res);
			}
		}
	}
	
	
	
	
}
