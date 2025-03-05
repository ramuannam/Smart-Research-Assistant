# 🔍 Smart Research Assistant – AI-Powered Browser Extension
Enhance your research workflow with AI-powered text summarization, Q&A, and note-saving – all within your browser!

### 🚀 Features
✅ AI-Powered Summarization – Get concise summaries of selected text instantly.\
✅ Ask Questions – Query selected content using LLM-backed intelligence.\
✅ Persistent Notes – Save and revisit summaries anytime within your browser.\
✅ Seamless Integration – Works on any webpage with a single click.

### 🏗️ Tech Stack
Frontend: Browser Extension (HTML, CSS, JavaScript)\
Backend: Java (Spring Boot), REST APIs\
AI Model: Gemini Flash 2.0 API\
Storage: Local browser storage for saved summaries

### ⚙️ How It Works
1. Select any text on a webpage.
2. Click the extension to choose an action (Summarize, Ask AI, Save).
3. AI processes the request via Spring Boot backend & Gemini Flash 2.0.
4. Instant response displayed within the extension popup.
5. Save important insights for future reference.

### 🛠️ Installation
1. Clone the repository:
 ```
  git clone https://github.com/yourusername/smart-research-assistant.git
 ```
2. Load the extension in your browser:
* Open Chrome and navigate to chrome://extensions/.
* Enable Developer Mode (toggle in the top-right).
* Click Load Unpacked and select the extension folder.

3. Run the backend server:
```
cd backend  
mvn spring-boot:run  
```
4. Start using the AI assistant! 🎉

### 📌 Future Enhancements
* Support for more AI models (GPT-4, Claude, etc.)
* Cloud-based storage for cross-device accessibility
* Integration with research tools like Notion, Obsidian

