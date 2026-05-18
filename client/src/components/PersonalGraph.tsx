import React, { useMemo } from "react";
import ReactFlow, { Background, Controls, type Edge, type Node } from "reactflow";
import "reactflow/dist/style.css";
import type { PersonalSkillGraphResponse } from "../types/api";

type Props = {
  graph?: PersonalSkillGraphResponse;
};

export default function PersonalGraph({ graph }: Props) {
  const nodes: Node[] = useMemo(() => {
    if (!graph) return [];

    return graph.nodes.map((node, index) => ({
      id: String(node.id),
      position: {
        x: 80 + (index % 4) * 130,
        y: 60 + Math.floor(index / 4) * 100,
      },
      data: {
        label: `${node.label}\n${node.months}ヶ月`,
      },
      style: {
        width: 70 + node.normalizedSize * 40,
        height: 70 + node.normalizedSize * 40,
        borderRadius: "50%",
        background: "#e9fff2",
        border: "2px solid #42b883",
        fontWeight: 700,
      },
    }));
  }, [graph]);

  const edges: Edge[] = useMemo(() => {
    if (!graph) return [];

    return graph.edges.map((edge) => ({
      id: `${edge.source}-${edge.target}`,
      source: String(edge.source),
      target: String(edge.target),
      label: `${edge.months}ヶ月`,
      style: {
        strokeWidth: 1 + edge.normalizedWeight * 5,
      },
    }));
  }, [graph]);

  return (
    <div className="card">
      <h3>個人スキルネットワーク</h3>

      <div className="personal-graph-box">
        <ReactFlow nodes={nodes} edges={edges} fitView>
          <Background />
          <Controls />
        </ReactFlow>
      </div>
    </div>
  );
}