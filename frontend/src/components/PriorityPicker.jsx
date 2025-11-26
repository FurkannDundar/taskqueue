function PriorityPicker({ priority, setPriority }) {
  return (
    <div className="flex items-center justify-center gap-4 p-6">
      <label className="text-lg font-semibold text-gray-700">
        Pick the Priority
      </label>
      <select
        value={priority}
        onChange={(event) => setPriority(event.target.value)}
        className="px-4 py-2 border-2 border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all cursor-pointer hover:border-gray-400 min-w-[200px]"
      >
        <option value="" disabled>
          Select priority...
        </option>
        <option value="LOW">🟢 Low</option>
        <option value="MEDIUM">🟡 Medium</option>
        <option value="HIGH">🟠 High</option>
        <option value="CRITICAL">🔴 Critical</option>
      </select>
    </div>
  );
}

export default PriorityPicker;
