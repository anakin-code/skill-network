import React, { useMemo } from "react";
import ReactFlow, {
  Background,
  Controls,
  MiniMap,
  type Edge,
  type Node,
} from "reactflow";
import "reactflow/dist/style.css";
import type { SkillGraphResponse } from "../types/api";

type Props = {
  graph: SkillGraphResponse;
  onSelectSkill: (skillName: string) => void;
};

export default function SkillGraph({ graph, onSelectSkill }: Props) {
  const nodes: Node[] = useMemo(() => {
    return graph.nodes.map((node, index) => ({
      id: String(node.id),
      position: {
        x: 120 + (index % 5) * 150,
        y: 80 + Math.floor(index / 5) * 110,
      },
      data: { label: node.label },
      style: {
        width: 90,
        height: 90,
        borderRadius: "50%",
        background: "#e8f0ff",
        border: "2px solid #6b8cff",
        color: "#1f2a44",
        fontWeight: 700,
      },
    }));
  }, [graph.nodes]);

  const edges: Edge[] = useMemo(() => {
    return graph.edges.map((edge) => ({
      id: `${edge.source}-${edge.target}`,
      source: String(edge.source),
      target: String(edge.target),
      animated: edge.weight >= 2,
      label: String(edge.weight),
      style: {
        strokeWidth: Math.max(1, edge.weight),
      },
    }));
  }, [graph.edges]);

  return (
    <div className="graph-box">
      <ReactFlow
        nodes={nodes}
        edges={edges}
        fitView
        onNodeClick={(_, node) => onSelectSkill(String(node.data.label))}
      >
        <Background />
        <Controls />
        <MiniMap />
      </ReactFlow>
    </div>
  );
}