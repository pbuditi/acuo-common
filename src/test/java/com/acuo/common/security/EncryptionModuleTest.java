package com.acuo.common.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.acuo.common.util.GuiceJUnitRunner;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ EncryptionModuleTest.SystemPropertyModule.class, EncryptionModule.class })
public class EncryptionModuleTest {

    public static class SystemPropertyModule extends AbstractModule {
        @Override
        protected void configure() {
            bindConstant().annotatedWith(Names.named("acuo.security.key")).to("mysecuritykey");
        }
    }

    @Inject
    PBEStringEncryptor encryptor;

    @Test
    public void testEncryptor() {
        String message = "this is the text I want to encrypt";
        String encrypted = encryptor.encrypt(message);
        assertThat(encrypted).isNotEqualTo(message);
        String decrypt = encryptor.decrypt(encrypted);
        assertThat(decrypt).isEqualTo(message);
    }

}