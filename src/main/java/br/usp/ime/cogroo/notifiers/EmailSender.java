package br.usp.ime.cogroo.notifiers;

import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

//import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.usp.ime.cogroo.dao.UserDAO;
import br.usp.ime.cogroo.model.User;
import br.usp.ime.cogroo.util.BuildUtil;
import br.usp.ime.cogroo.util.CriptoUtils;

@Component
// @ApplicationScoped
class EmailSender {

	private static final Logger LOG = Logger.getLogger(EmailSender.class);

	public static final String FOOTER = "<br>"
			+ "_______________________________________________<br>"
			+ "CoGrOO Comunidade &lt;<a href=\""
			+ BuildUtil.BASE_URL
			+ "\">"
			+ BuildUtil.BASE_URL
			+ "</a>&gt;<br>"
			+ "CoGrOO é o Corretor Gramatical para o Apache Open|LibreOffice. Você é parte dessa comunidade!<br>"
			+ "Curta o CoGrOO no <a href='http://www.facebook.com/pages/CoGrOO/191205774239878'>Facebook</a>, "
			+ "acompanhe a movimentação da Comunidade <br>no <a href='http://twitter.com/cogrcom'>@CoGrCom</a> "
			+ "e siga o <a href='http://twitter.com/cogroo'>@CoGrOO</a> para novidades do projeto!<br />";

	private final static String FROM_NAME = "CoGrOO Comunidade";
	private final static String SUBJECT_PREFFIX = "[CoGrOO Comunidade] ";
	private final static String BASE_EMAIL;
	private final static String NOREPLY_EMAIL;
	final static String SMTP = "smtp.cogroo.org";
	private Queue<Email> emailQueue = new ConcurrentLinkedQueue<Email>();

	private static final String DKIM_HEADER_TEMPLATE = "v=1; c=simple/simple; s=key1; d=cogroo.org; h=from:to:subject; a=rsa-sha1; bh=; b=;";
	private static final String DKIM_PRIVATE_KEY = BuildUtil.DKIM_PRIVATE_KEY;

	private static List<InternetAddress> REPLYTO;

	private UserDAO userDAO;

	static {

		BASE_EMAIL = BuildUtil.EMAIL_SYSTEM_USR;
		NOREPLY_EMAIL = BASE_EMAIL.replace("@", "-noreply@");
		try {
			REPLYTO = Collections.singletonList(new InternetAddress(
					NOREPLY_EMAIL));
		} catch (AddressException e) {
			LOG.error("Failed to set REPLYTO address: " + NOREPLY_EMAIL, e);
		}
	}

	public EmailSender(UserDAO userDAO) {
		this.userDAO = userDAO;

		int delay = 0;
		int period = 15000; // repeat every 15 sec (less than 5 email per
							// minute).
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {

				if (!emailQueue.isEmpty()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("There are " + emailQueue.size()
								+ " pending emails. Sending one now.");
					}
					Email email = emailQueue.poll();
					if (email != null) {
						try {
							String res = email.send();
							if (LOG.isDebugEnabled()) {
								LOG.debug("Sent email: " + res);
							}
						} catch (EmailException e) {
							LOG.error("Failed to send email.", e);
						}
					}
				}

			}
		}, delay, period);
	}

	public void sendEmail(String body, String subject, User user) {
		if (user.getEmail() == null)
			return;

		try {
			// toEmail = "check-auth@verifier.port25.com";
			Email email = createEmail(body, subject, user);
			// add it to the send queue
			emailQueue.add(email);

		} catch (EmailException e) {
			LOG.error("Failed to send email. toEmail: " + user.getEmail(), e);
		}
	}

	Email createEmail(String body, String subject, User user)
			throws EmailException {
		String userMail = user.getEmail().trim();
		String link = createUnsubscribeLink(user);
		String msg = body + createFooter(link);

		DKimSimpleMail email = new DKimSimpleMail();
		email.setHostName(SMTP);
		email.setDebug(LOG.isDebugEnabled());
		email.setSSL(true);
		email.addTo(userMail);
		email.setAuthentication(BASE_EMAIL, BuildUtil.EMAIL_SYSTEM_PWD);
		email.setFrom(BuildUtil.EMAIL_SYSTEM_USR, FROM_NAME);
		// email.setReplyTo(REPLYTO);
		email.setReplyTo(REPLYTO);
		email.setSubject(SUBJECT_PREFFIX + subject);
		email.setContent(msg, Email.TEXT_HTML);

		email.addHeader("List-Unsubscribe", "<" + link + ">");

		if (userMail.endsWith("hotmail.com"))
			email.setCharset(Email.ISO_8859_1);
		else
			email.setCharset("UTF-8");

		email.setKDimSigner(DKIM_HEADER_TEMPLATE, DKIM_PRIVATE_KEY);
		return email;
	}

	public void sendEmail(String body, String subject, Set<User> users) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Will send a batch of emails.");
		}

		for (User user : users) {
			sendEmail(body, subject, user);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("ThreadedMailSender started!");
		}
	}

	public String createFooter(String link) {
		StringBuilder sb = new StringBuilder(FOOTER);

		sb.append(
				"Caso não queira mais receber estas notificações clique <a href=\"")
				.append(link).append("\">aqui</a>");

		return sb.toString();

	}

	private String createUnsubscribeLink(User user) {
		StringBuilder sb = new StringBuilder();

		sb.append(BuildUtil.BASE_URL).append("unsubscribe/")
				.append(user.getId()).append("/")
				.append(getOrGenerateOptoutCode(user));

		return sb.toString();
	}

	private String getOrGenerateOptoutCode(User user) {
		String code = user.getEmailOptOutCode();

		if (code == null) {
			code = CriptoUtils.generateHash(user);
			user.setEmailOptOutCode(code);
			userDAO.update(user);
		}

		return code;
	}

}
