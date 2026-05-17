import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { createQuestion } from "../api/questionApi";
import { buttonStyle, formStyle, inputStyle } from "../styles/commonStyles";

function QuestionCreatePage() {
  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const submitHandler = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorMessage("");

    const userKey = localStorage.getItem("userKey");

    if (!userKey) {
      setErrorMessage("ログインしてください。");
      return;
    }

    if (title.trim() === "") {
      setErrorMessage("タイトルを入力してください。");
      return;
    }

    if (content.trim() === "") {
      setErrorMessage("本文を入力してください。");
      return;
    }

    try {
      await createQuestion(title, content, userKey);
      navigate("/questions");
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      }
    }
  };

  return (
    <div>
      <h2>質問投稿</h2>

      <form onSubmit={submitHandler} style={formStyle}>
        <input
          value={title}
          onChange={(event) => setTitle(event.target.value)}
          placeholder="タイトル"
          style={inputStyle}
        />

        <textarea
          value={content}
          onChange={(event) => setContent(event.target.value)}
          placeholder="質問内容"
          rows={6}
          style={inputStyle}
        />

        {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}

        <button type="submit" style={buttonStyle}>
          投稿
        </button>
      </form>
    </div>
  );
}

export default QuestionCreatePage;