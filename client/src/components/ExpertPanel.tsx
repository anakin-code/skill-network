import type { ExpertResponse } from "../types/api";

type Props = {
  selectedSkill: string;
  experts: ExpertResponse[];
};

export default function ExpertPanel({ selectedSkill, experts }: Props) {
  return (
    <div className="card">
      <h3>有識者検索</h3>
      {!selectedSkill && <p className="muted">スキルノードをクリックしてください</p>}
      {selectedSkill && <p>選択中: <strong>{selectedSkill}</strong></p>}
      <div className="similar-list">
        {experts.map((expert) => (
          <div key={expert.hrid} className="similar-item">
            <strong>{expert.name}</strong>
            <span>{expert.totalMonths}ヶ月</span>
            <small>{expert.rankName} / {expert.mailAddress}</small>
          </div>
        ))}
      </div>
    </div>
  );
}
