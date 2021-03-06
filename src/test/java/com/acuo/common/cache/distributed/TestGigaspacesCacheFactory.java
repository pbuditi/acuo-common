/*
 * Copyright 2005 Ralf Joachim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acuo.common.cache.distributed;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.acuo.common.cache.base.Cache;
import com.acuo.common.cache.base.CacheAcquireException;
import com.acuo.common.cache.base.CacheFactory;

/**
 * @author <a href="mailto:ralf DOT joachim AT syscon DOT eu">Ralf Joachim</a>
 * @version $Revision: 9041 $ $Date: 2011-08-16 11:51:17 +0200 (Di, 16 Aug 2011) $
 * @since 1.0
 */
public final class TestGigaspacesCacheFactory extends TestCase {
    private static final boolean DISABLE_LOGGING = true;
    
    public static Test suite() {
        TestSuite suite = new TestSuite("CoherenceCacheFactory Tests");

        suite.addTest(new TestGigaspacesCacheFactory("testConstructor"));
        suite.addTest(new TestGigaspacesCacheFactory("testGetCacheType"));
        suite.addTest(new TestGigaspacesCacheFactory("testGetCacheClassName"));
        suite.addTest(new TestGigaspacesCacheFactory("testGetCache"));
        suite.addTest(new TestGigaspacesCacheFactory("testShutdown"));

        return suite;
    }

    public TestGigaspacesCacheFactory(final String name) { super(name); }

    public void testConstructor() {
        CacheFactory<String, String> cf = new GigaspacesCacheFactory<String, String>();
        assertTrue(cf instanceof GigaspacesCacheFactory);
    }

    public void testGetCacheType() {
        CacheFactory<String, String> cf = new GigaspacesCacheFactory<String, String>();
        assertEquals("gigaspaces", cf.getCacheType());
    }

    public void testGetCacheClassName() {
        CacheFactory<String, String> cf = new GigaspacesCacheFactory<String, String>();
        String classname = "com.acuo.common.cache.distributed.GigaspacesCache";
        assertEquals(classname, cf.getCacheClassName());
    }

    public void testGetCache() {
        CacheFactory<String, String> cf = new GigaspacesCacheFactory<String, String>();
        try {
            Cache<String, String> c = cf.getCache(null);
            assertTrue(c instanceof GigaspacesCache);
        } catch (CacheAcquireException ex) {
            fail("Failed to get instance of GigaspacesCache from factroy");
        }
    }

    public void testShutdown() {
        Logger logger = Logger.getLogger(GigaspacesCacheFactory.class);
        Level level = logger.getLevel();
        
        GigaspacesCacheFactory<String, String> cf = new GigaspacesCacheFactory<String, String>();
        try {
            cf.getCache(null);
        } catch (CacheAcquireException ex) {
            fail("Failed to get instance of GigaspacesCache from factroy");
        }
        int counter = DistributedCacheFactoryMock.getCounter();
        
        if (DISABLE_LOGGING) { logger.setLevel(Level.FATAL); }

        DistributedCacheFactoryMock.setException(null);
        cf.shutdown(Object.class.getName());
        assertEquals(counter, DistributedCacheFactoryMock.getCounter());
        
        DistributedCacheFactoryMock.setException(new Exception("dummy"));
        cf.shutdown(DistributedCacheFactoryMock.class.getName());
        assertEquals(counter, DistributedCacheFactoryMock.getCounter());

        logger.setLevel(level);
        
        DistributedCacheFactoryMock.setException(null);
        cf.shutdown(DistributedCacheFactoryMock.class.getName());
        assertEquals(counter + 1, DistributedCacheFactoryMock.getCounter());
    }
}
