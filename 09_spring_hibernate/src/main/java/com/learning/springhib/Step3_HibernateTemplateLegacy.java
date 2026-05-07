package com.learning.springhib;

/*
 * Step 3: a note, not runnable code.
 *
 * Old Spring + Hibernate codebases (Spring 2.x / Hibernate 3.x era)
 * used a class called HibernateTemplate — a thin JdbcTemplate-style
 * wrapper around SessionFactory:
 *
 *     HibernateTemplate hib = new HibernateTemplate(sessionFactory);
 *     User u = hib.get(User.class, 1L);
 *     hib.save(new User(...));
 *
 * Why it existed:
 *   - Hibernate 2/3 threw checked HibernateExceptions; HibernateTemplate
 *     translated them to unchecked DataAccessException.
 *   - It hid the boilerplate of "open session, run code, close session".
 *
 * Why it's gone:
 *   - Modern Hibernate (3.0.1+) already throws unchecked exceptions.
 *   - SessionFactory.getCurrentSession() + @Transactional handles
 *     the lifecycle better than HibernateTemplate ever did.
 *   - Spring 5 deprecated HibernateTemplate; it's removed in spring-orm
 *     for Hibernate 6.
 *
 * What this means for you:
 *   - In NEW code, inject SessionFactory, use getCurrentSession(), wrap
 *     methods in @Transactional. (That's exactly Step 1 + Step 2.)
 *   - When you find HibernateTemplate in a legacy project, you'll know
 *     what it was for and that it can usually be replaced with the
 *     pattern above.
 *
 * No main() — there is nothing to demo. This file is documentation in
 * source form so it sits next to its siblings.
 */
public final class Step3_HibernateTemplateLegacy {
    private Step3_HibernateTemplateLegacy() { }
}
