# EffortLogger-CSE360

EffortLogger-CSE360 is a simple but powerful tool to help teams and individuals track their efforts and defects across multiple projects. Designed with modern technology and practices, it improves the old EffortLogger system for today's needs in project tracking, accounting, and workforce management.

## What Does It Do?

EffortLogger lets you log your working time and defects for many projects. It is flexible and can show you how your team spends effort on tasks, life cycle steps, and deliverables. Data is securely stored in a MySQL database, making analysis and reporting easy.

- **Track efforts:** Log time on activities like planning, design, implementation, meetings, interruptions, etc.
- **Defect logging:** Record and analyze defects to improve software quality.
- **Project management:** Manage several projects, each with their own logs.
- **Data analysis:** See where your resources go, generate reports, and identify room for improvement.

## Key Features

- Capture a week's effort and defects for up to ten projects.
- Log and analyze up to 995 entries per project.
- Easy-to-use interface built with JavaFX.
- Integration with MySQL for secure and reliable data storage.
- Support for life cycle steps, deliverable tracking, and effort categories.
- View, edit, and update existing log entries.
- Customize categories (such as plans, deliverables, interruptions) for your workflow.
- Foundation inspired by Watts Humphrey’s *A Discipline for Software Engineering*.

## Technologies Used

- Java 11+
- JavaFX for the Desktop UI
- MySQL (JDBC connection)
- Visual Basic (for Excel customization and advanced analysis)
- Compatible with Windows, MacOS, Ubuntu

## How Does It Work?

The app uses a MySQL database to store user information and activity logs. Main UI features include:

- Log effort by activity, date, and time
- Record defects for analysis
- Specify project life cycles and deliverables
- View, filter, and edit previous log entries
- Analytical tools to visualize effort distribution and defects

New users can sign up securely. Credentials are checked against the database.

## System Requirements

- Minimum 4GB RAM, Dual-core processor
- MySQL server
- Java 11 or above
- JavaFX libraries

## Getting Started

1. **Install MySQL and create a database named `effort-logger`.**
2. **Clone this repository.**
3. **Edit password information in `DataBaseUtils.java` to match your MySQL setup.**
4. **Build and run the application using your preferred Java IDE.**

*Note: You may customize further analysis and reporting in Excel using Visual Basic.*

## Sample Usage

1. Log in or sign up as a user.
2. Start a new project or select an existing one.
3. Add effort entries: choose activity (like designing, coding, meeting), category, plan, deliverable, and time.
4. Record any defects found and fill in details.
5. View and analyze your team's performance with built-in filters or export data.

---

EffortLogger-CSE360 simplifies tracking work and defects for software engineering projects. It helps teams boost productivity and grow by learning from their data.
