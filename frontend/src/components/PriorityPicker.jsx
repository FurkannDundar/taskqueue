function PriorityPicker({ priority, setPriority }) {
  return (
    <div className="space-y-3">
      <label className="block text-sm font-semibold text-gray-700">
        Öncelik Durumu
      </label>
      <select
        value={priority}
        onChange={(event) => setPriority(event.target.value)}
        className="w-full px-4 py-3 bg-white border-2 border-gray-300 rounded-lg shadow-sm
                   focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 
                   hover:border-gray-400 transition-all cursor-pointer
                   text-gray-700 font-medium"
      >
        <option value="" disabled>
          Select priority...
        </option>
        <option value="CRITICAL">🔴 Critical</option>
        <option value="HIGH">🟠 High</option>
        <option value="MEDIUM">🟡 Medium</option>
        <option value="LOW">🟢 Low</option>
      </select>
    </div>
  );
}

export default PriorityPicker;
