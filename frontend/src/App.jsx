import { useState } from "react";
import "./App.css";

function App() {
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);

  const handleSend = async (e) => {
    e.preventDefault();

    if (!message.trim()) return;

    const userMessage = message;

    setMessages((prev) => [...prev, { sender: "user", text: userMessage }]);
    setMessage("");

    try {
      const response = await fetch("http://localhost:8080/api/chat", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          message: userMessage,
        }),
      });

      if (!response.ok) {
        throw new Error("Failed to get AI response");
      }

      const aiReply = await response.text();

      setMessages((prev) => [...prev, { sender: "ai", text: aiReply }]);
    } catch (error) {
      console.error("Chat error:", error);
      setMessages((prev) => [
        ...prev,
        {
          sender: "ai",
          text: "Sorry, something went wrong. Please try again.",
        },
      ]);
    }
  };

  return (
    <div className="app">
      <header className="header">
        <h2>AI Chatbot 🤖</h2>
        <p>Your AI assistant</p>
      </header>

      <main className="chat-container">
        {messages.length === 0 ? (
          <div className="welcome">
            <h1>
              Hello! <span className="wave">👋</span>
            </h1>
            <p>How can I help you today?</p>
          </div>
        ) : (
          <div className="messages">
            {messages.map((msg, index) => (
              <div
                className={
                  msg.sender === "user" ? "user-message" : "ai-message"
                }
                key={index}
              >
                {msg.text}
              </div>
            ))}
          </div>
        )}
      </main>

      <form className="chat-input" onSubmit={handleSend}>
        <input
          type="text"
          placeholder="Ask me anything..."
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        />
        <button type="submit">Send</button>
      </form>
    </div>
  );
}

export default App;
