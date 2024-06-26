package com.agilevolve.domain.common.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.agilevolve.domain.common.mail.DefaultMailManager;
import com.agilevolve.domain.common.mail.Mailer;

import freemarker.template.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class DefaultMailManagerTests {

  @TestConfiguration
  static class DefaultMessageCreatorConfiguration {
    @Bean
    FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
      FreeMarkerConfigurationFactoryBean factoryBean = new FreeMarkerConfigurationFactoryBean();
      factoryBean.setTemplateLoaderPath("/mail-templates/");
      return factoryBean;
    }
  }

  @Autowired
  private Configuration configuration;
  private Mailer mailerMock;
  private DefaultMailManager instance;

  @BeforeEach
  public void setup() {
    mailerMock = mock(Mailer.class);
    instance = new DefaultMailManager("noreply@agilevolve.com", mailerMock, configuration);
  }

  @Test
  public void send_nullEmailAddress_shouldFail() {
    assertThrows(IllegalArgumentException.class, () -> {
      instance.send(null, "Test subject", "test.ftl");
    });
  }

  @Test
  public void send_emptyEmailAddress_shouldFail() {
    assertThrows(IllegalArgumentException.class, () -> {
      instance.send("", "Test subject", "test.ftl");
    });
  }

  @Test
  public void send_nullSubject_shouldFail() {
    assertThrows(IllegalArgumentException.class, () -> {
      instance.send("test@agilevolve.com", null, "test.ftl");
    });
  }

  @Test
  public void send_emptySubject_shouldFail() {
    assertThrows(IllegalArgumentException.class, () -> {
      instance.send("test@agilevolve.com", "", "test.ftl");
    });
  }

  @Test
  public void send_nullTemplateName_shouldFail() {
    assertThrows(IllegalArgumentException.class, () -> {
      instance.send("test@agilevolve.com", "Test subject", null);
    });
  }

  @Test
  public void send_emptyTemplateName_shouldFail() {
    assertThrows(IllegalArgumentException.class, () -> {
      instance.send("test@agilevolve.com", "Test subject", "");
    });
  }

  // @Test
  // public void send_validParameters_shouldSucceed() {
  //   String to = "user@example.com";
  //   String subject = "Test subject";
  //   String templateName = "test.ftl";

  //   instance.send(to, subject, templateName, MessageVariable.from("name", "test"));
  //   ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
  //   verify(mailerMock).send(messageArgumentCaptor.capture());

  //   Message messageSent = messageArgumentCaptor.getValue();
  //   assertEquals(to, messageSent.getTo());
  //   assertEquals(subject, messageSent.getSubject());
  //   assertEquals("noreply@agilevolve.com", messageSent.getFrom());
  //   assertEquals("Hello, test\n", messageSent.getBody());
  // }
}
