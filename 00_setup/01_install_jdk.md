# Step 1 — Install the JDK

> *The JDK is the toolbox; without it, none of this compiles.*

We use **JDK 17** — the long-term-support release that Spring Boot 3.x requires. Anything older than 17 will silently break the later phases.

---

## Install

**Windows (recommended path):**

1. Download the JDK 17 installer — Eclipse Temurin or Oracle JDK both work.
2. Install with default options.
3. The installer typically sets `JAVA_HOME` automatically. If not, see below.

**Verify:**

```powershell
java -version
javac -version
```

You should see something like `openjdk version "17.0.x"`. Any version `1.8` or `11` here means an older JDK is on your PATH first — fix that before continuing.

---

## Setting `JAVA_HOME` manually (Windows)

```powershell
# In an Admin PowerShell:
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Eclipse Adoptium\jdk-17.0.x", "Machine")
```

Then restart the terminal and check:

```powershell
echo $env:JAVA_HOME
```

---

## JDK vs JRE vs JVM — quick refresher

| Term | What it is |
|------|-----------|
| **JVM** | The runtime that *executes* `.class` bytecode |
| **JRE** | JVM + standard libraries (enough to *run* Java apps) |
| **JDK** | JRE + compiler (`javac`) + tools (enough to *develop* Java apps) |

You need the **JDK** because you'll be compiling code, not just running it.

---

> Next: [02_maven_basics.md](02_maven_basics.md)
