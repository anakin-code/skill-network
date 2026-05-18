import type { ExpertResponse } from "../types/api";

type Props = {
  selectedSkill: string;
  experts: ExpertResponse[];
};

export default function ExpertPanel({ selectedSkill, experts }: Props) {
  return (
    <div className="card">
      <h3>有識者ランキング</h3>

      {!selectedSkill && <p className="muted">スキルを選択してください</p>}

      {selectedSkill && (
        <p>
          選択中: <strong>{selectedSkill}</strong>
        </p>
      )}

      <div className="expert-list">
        {experts.map((expert, index) => (
          <div key={expert.hrid} className="expert-item">
            <span className="rank">{index + 1}</span>
            <div>
              <strong>{expert.name}</strong>
              <p>{expert.rankName}</p>
              <small>{expert.skillNames}</small>
            </div>
            <span className="score">{expert.score}</span>
          </div>
        ))}
      </div>
    </div>
  );
}