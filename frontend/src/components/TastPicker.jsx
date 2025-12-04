function TaskPicker({ task, setTask }) {
  const tasks = [
    "SEND_EMAIL",
    "PREPARE_REPORT",
    "SEND_PRIVATE_MESSAGE",
    "FETCH_DATA",
    "GET_LAST_1000_RECORDS",
    "PROCESS_IMAGE",
    "GENERATE_PDF",
    "BACKUP_DATABASE",
  ];

  return (
    <div className="space-y-3">
      <label className="block text-sm font-semibold text-gray-700">
        Task Seçiniz
      </label>
      <select
        value={task}
        onChange={(event) => setTask(event.target.value)}
        className="w-full px-4 py-3 bg-white border-2 border-gray-300 rounded-lg shadow-sm
                   focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 
                   hover:border-gray-400 transition-all cursor-pointer
                   text-gray-700 font-medium"
      >
        <option value="" disabled>
          Select a task type...
        </option>
        {tasks.map((t) => (
          <option key={t} value={t}>
            {t.replace(/_/g, " ")}
          </option>
        ))}
      </select>
    </div>
  );
}

export default TaskPicker;
