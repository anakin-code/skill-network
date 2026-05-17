import { useState } from "react";
import { Link } from "react-router-dom";

import { searchQuestions } from "../api/questionApi";
import type { Question } from "../types/question";
import { cardStyle } from "../styles/commonStyles";

function SearchPage() {
  const [keyword, setKeyword] = useState("");
  const [questions, setQuestions] = useState<Question[]>([]);
  const [errorMessage, setErrorMessage] = useState("");

  const searchHandler = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorMessage("");

    if (keyword.trim() === "") {
      setErrorMessage("検索キーワードを入力してください。");
      return;
    }

    try {
      const data = await searchQuestions(keyword);
      setQuestions(data);
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      }
    }
  };

  return (
    <div>
      <h2>検索</h2>

      <form
        onSubmit={searchHandler}
        style={{ display: "flex", gap: 8, marginBottom: 24 }}
      >
        <input
          value={keyword}
          onChange={(event) => setKeyword(event.target.value)}
          placeholder="キーワード"
          style={{ flex: 1, padding: 12 }}
        />

        <button type="submit">検索</button>
      </form>

      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}

      {questions.map((question) => (
        <Link
          key={question.id}
          to={`/questions/${question.id}`}
          style={{ textDecoration: "none", color: "inherit" }}
        >
          <div style={cardStyle}>
            <h3>{question.title}</h3>
            <p>{question.content}</p>

            {question.resolved ? (
              <strong style={{ color: "green" }}>解決済み</strong>
            ) : (
              <strong style={{ color: "red" }}>未解決</strong>
            )}
          </div>
        </Link>
      ))}
    </div>
  );
}

export default SearchPage;