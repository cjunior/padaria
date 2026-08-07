import { DataTable, ApiForm, PageBreadcrumb } from "../components";
import { Plus, X } from "lucide-react";
import { useState } from "react";
import { createPortal } from "react-dom";
import { vendasService } from "../services/vendas.service";
import { clientesService } from "../services/clientes.service";

export default function VendasPage() {
  const [createOpen, setCreateOpen] = useState(false);
  const [reloadToken, setReloadToken] = useState(0);
  const closeCreateDialog = () => setCreateOpen(false);


  return (
    <div className="page">

      <PageBreadcrumb items={[{ label: `Listagem de Vendas` }]} />

      <header className="page-header">
        <div>
          <h1 className="page-title">{`Listagem de Vendas`}</h1>
        </div>
      </header>
      <p style={{ margin: "-18px 0 20px 0" }}>{`Gerencie vendas cadastrados, revise status e execute ações recorrentes.`}</p>
      <div className="page-actions">
        <button className="btn btn-primary" type="button" onClick={() => setCreateOpen(true)}>
          <Plus size={16} aria-hidden="true" />
          Adicionar
        </button>
      </div>
      <DataTable load={vendasService.list} reloadToken={reloadToken} columns={[{"key":"id","label":"id"},{"key":"clienteId","label":"clienteId"},{"key":"valor_total","label":"valor_total"},{"key":"status","label":"status"},{"key":"forma_pagamento","label":"forma_pagamento"}]} idKey={"id"} fields={[{"name":"clienteId","type":"number","required":false,"readOnly":false,"relation":{"endpoint":"/clientes","valueKey":"id","labelKey":"nome"}},{"name":"valor_total","type":"number","required":true,"readOnly":false},{"name":"status","type":"text","required":true,"readOnly":false},{"name":"forma_pagamento","type":"text","required":false,"readOnly":false}]} relationLoaders={{ "clienteId": clientesService.list }} onUpdate={vendasService.update} onDelete={vendasService.remove} />
      {createOpen && createPortal(
        <div className="dialog-overlay" role="presentation" onClick={closeCreateDialog}>
          <section
            className="dialog-content shadcn-dialog"
            role="dialog"
            aria-modal="true"
            aria-labelledby="create-dialog-title"
            onClick={(event) => event.stopPropagation()}
          >
            <header className="dialog-header">
              <div>
                <h2 id="create-dialog-title" className="dialog-title">Adicionar</h2>
                <p className="dialog-description">Preencha os dados para criar um novo registro.</p>
              </div>
              <button className="icon-btn" type="button" onClick={closeCreateDialog} aria-label="Fechar">
                <X size={16} aria-hidden="true" />
              </button>
            </header>
            <ApiForm
              submit={vendasService.create}
              fields={[{"name":"clienteId","type":"number","required":false,"readOnly":false,"relation":{"endpoint":"/clientes","valueKey":"id","labelKey":"nome"}},{"name":"valor_total","type":"number","required":true,"readOnly":false},{"name":"status","type":"text","required":true,"readOnly":false},{"name":"forma_pagamento","type":"text","required":false,"readOnly":false}]}
              submitLabel="Adicionar"
              relationLoaders={{ "clienteId": clientesService.list }}
              onSuccess={() => {
                setCreateOpen(false);
                setReloadToken((value) => value + 1);
              }}
            />
          </section>
        </div>,
        document.body
      )}
    </div>
  );
}
