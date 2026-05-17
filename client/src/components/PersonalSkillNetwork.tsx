import { useEffect, useMemo, useState } from "react";
import ReactFlow, {
  Background,
  Controls
} from "reactflow";
import "reactflow/dist/style.css";

type SkillNode = {
  id: number;
  label: string;
  months: number;
  normalizedSize: number;
};

type SkillEdge = {
  source: number;
  target: number;
  months: number;
  normalizedWeight: number;
};

type SkillNetworkResponse = {
  hrid: string;
  nodes: SkillNode[];
  edges: SkillEdge[];
};

export default function PersonalSkillNetwork() {
  const [data, setData] = useState<SkillNetworkResponse | null>(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/skill-network/profile/A001")
      .then((res) => res.json())
      .then(setData)
      .catch(console.error);
  }, []);

  const nodes = useMemo(() => {
    if (!data) return [];

    return data.nodes.map((skill, index) => {
      const size = 50 + skill.normalizedSize * 50;

      return {
        id: String(skill.id),
        position: {
          x: Math.cos(index * 2) * 200 + 300,
          y: Math.sin(index * 2) * 200 + 250,
        },
        data: {
          label: `${skill.label}\n${skill.months}ヶ月`,
        },
        style: {
          width: size,
          height: size,
          borderRadius: "50%",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          textAlign: "center",
          fontSize: 12,
        },
      };
    });
  }, [data]);

  const edges = useMemo(() => {
    if (!data) return [];

    return data.edges.map((edge) => ({
      id: `${edge.source}-${edge.target}`,
      source: String(edge.source),
      target: String(edge.target),
      label: `${edge.months}ヶ月`,
      style: {
        strokeWidth: 1 + edge.normalizedWeight * 5,
      },
    }));
  }, [data]);

  return (
    <div style={{ width: "100%", height: "600px" }}>
      <h2>個人スキルネットワーク</h2>
      <ReactFlow nodes={nodes} edges={edges} fitView>
        <Background />
        <Controls />
      </ReactFlow>
    </div>
  );
}